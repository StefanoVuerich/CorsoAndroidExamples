#include <assert.h>
#include <jni.h>
#include <string.h>

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

#include <sys/types.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>

//utility constants
#define true 1
#define false 0

//engine interfaces
static SLObjectItf engineObject = NULL;
static SLEngineItf engineEngine;

// output mix interfaces
static SLObjectItf outputMixObject = NULL;
static SLEnvironmentalReverbItf outputMixEnvironmentalReverb = NULL;

// file descriptor player interfaces
static SLObjectItf fdPlayerObject = NULL;
static SLPlayItf fdPlayerPlay = NULL;
static SLSeekItf fdPlayerSeek = NULL;
static SLMuteSoloItf fdPlayerMuteSolo = NULL;
static SLVolumeItf fdVolumeItf = NULL;
static SLDynamicInterfaceManagementItf dynamicInterfaceManagementItf = NULL;
static SLPrefetchStatusItf fdPrefetchStatusItf = NULL;
static SLPlaybackRateItf fdPlaybackRateItf = NULL;
static SLRatePitchItf fdSLRatePitchItf = NULL;

/*
 * check the error. If any errors exit the application!
 */
void CheckErr(SLresult res) {
	if (SL_RESULT_SUCCESS != res) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --ERRORE--");
	}
	if (SL_RESULT_PARAMETER_INVALID == res) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE parameter invalid");
	}
}

void getRate() {
	SLresult result;

	if (NULL != fdPlayerObject) {
		// get the pitch interface
		result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
				SL_IID_PLAYBACKRATE, &fdPlaybackRateItf);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE pitch interface");

		/*result = (*fdPlaybackRateItf)->SetPropertyConstraints(fdPlaybackRateItf, SL_RATEPROP_NOPITCHCORAUDIO);
		 CheckErr(result);
		 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		 ">>NATIVE pitch property constrain");*/

		SLpermille minRate;
		SLpermille maxRate;
		SLpermille stepSize;
		SLuint32 capabilities;

		result = (*fdPlaybackRateItf)->GetRateRange(fdPlaybackRateItf, 0,
				(void*) &minRate, (void*) &maxRate, (void*) &stepSize,
				(void*) &capabilities);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE rate min %lu , rate max %lu, stepsize %lu, capabilities %d",
				(unsigned long) minRate, (unsigned long) maxRate,
				(unsigned long) stepSize, (int) capabilities);

		SLuint32 properties;

		result = (*fdPlaybackRateItf)->GetProperties(fdPlaybackRateItf,
				(void*) &properties);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE pitch proprieties %d", (int) properties);

		result = (*fdPlaybackRateItf)->SetPropertyConstraints(fdPlaybackRateItf,
				properties);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE pitch core audio ok");

		SLpermille rate;
		result = (*fdPlaybackRateItf)->GetRate(fdPlaybackRateItf,
				(void*) &rate);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE current rate id %lu", (unsigned long) rate);

		result = (*fdPlaybackRateItf)->SetRate(fdPlaybackRateItf, 1500);
		CheckErr(result);

		result = (*fdPlaybackRateItf)->GetRate(fdPlaybackRateItf,
				(void*) &rate);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE current rate id %lu", (unsigned long) rate);
	}
}

/*
 * CREATE ENGINE OBJECT
 */

void Java_com_example_customviewcircles_nativeaudio_OpenSLES_createEngine(
		JNIEnv* env, jclass clazz) {

	SLresult result;
	// create engine
	result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
	assert(SL_RESULT_SUCCESS == result);
	(void) result;

	// realize the engine
	result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Engine Realized");

	// get the engine interface, which is needed in order to create other objects
	result = (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE,
			&engineEngine);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Ok get engine interface");

	// query for supported interface number
	SLuint32 numInterface;
	result = (*engineEngine)->QueryNumSupportedInterfaces(engineEngine, SL_OBJECTID_AUDIOPLAYER ,(void*) &numInterface);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Num Interfaces %d", (int) numInterface);

	SLInterfaceID interfaceId;
	int i;
	int j = (int) numInterface;
	for (i = 0; i < j; ++i) {

		result = (*engineEngine)->QuerySupportedInterfaces(engineEngine, SL_OBJECTID_AUDIOPLAYER ,i, (void*) &interfaceId);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE Interface %d supported", (int) interfaceId);

		/*SL_IID_DYNAMICINTERFACEMANAGEMENT   SL_IID_OBJECT SL_IID_ENGINE SL_IID_ENGINECAPABILITIES  SL_IID_THREADSYNC  SL_IID_AUDIOIODEVICECAPABILITIES */

		//ss_iid_pitch not supported

		if ((long) interfaceId == (long) SL_IID_PLAY) {
			__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
					">>NATIVE playitf supported %d",(long) SL_IID_PLAY);
		}
		if ((long) interfaceId == (long) SL_IID_PLAYBACKRATE) {
			__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
					">>NATIVE playback supported %d",(long)SL_IID_PLAYBACKRATE);
		}
	}

	// create output mix, with environmental reverb specified as a non-required interface
	const SLInterfaceID ids[] = { SL_IID_ENVIRONMENTALREVERB };
	const SLboolean req[] = { SL_BOOLEAN_FALSE };
	result = (*engineEngine)->CreateOutputMix(engineEngine, &outputMixObject, 1,
			ids, req);
	CheckErr(result);

	// realize the output mix
	result = (*outputMixObject)->Realize(outputMixObject, SL_BOOLEAN_FALSE);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Realized outputMixer");

	//get volume interface
	result = (*outputMixObject)->GetInterface(outputMixObject,
			SL_IID_ENVIRONMENTALREVERB, &outputMixEnvironmentalReverb);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Interface Volume ok");
}

/*
 * SONG LOADED CALLBACK
 */

void GetDurationCallback(SLPlayItf caller, void *pContext, SLuint32 event) {

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE sufficient data");
	getSampleDuration();
	getRate();
}

// set the playing state for the asset audio player
void setPlayingAssetAudioPlayer() {
	SLresult result;

	// make sure the asset audio player was created
	if (NULL != fdPlayerPlay) {

		// set the player's state
		result = (*fdPlayerPlay)->SetPlayState(fdPlayerPlay,
				SL_PLAYSTATE_PLAYING);
		CheckErr(result);
	}
	/*if (NULL != fdPlaybackRateItf) {
		SLpermille rate;
		result = (*fdPlaybackRateItf)->GetRate(fdPlaybackRateItf,
				(void*) &rate);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE last rate id %lu", (unsigned long) rate);
	}*/
}

// stop the player
void stopAssetAudioPlayer() {
	SLresult result;

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", "stoppo canzone");

	// make sure the asset audio player was created
	if (NULL != fdPlayerPlay) {

		// set the player's state
		result = (*fdPlayerPlay)->SetPlayState(fdPlayerPlay,
				SL_PLAYSTATE_STOPPED);
		CheckErr(result);

		if (NULL != fdPlayerObject) {
			(*fdPlayerObject)->Destroy(fdPlayerObject);
			fdPlayerObject = NULL;
			fdPlayerPlay = NULL;
			fdPlayerMuteSolo = NULL;
			fdPlayerSeek = NULL;
			dynamicInterfaceManagementItf = NULL;
			fdVolumeItf = NULL;
			fdPrefetchStatusItf = NULL;
			fdPlaybackRateItf = NULL;
			fdSLRatePitchItf = NULL;
		};
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja", "player distrutto");
	}
}

int getSampleDuration() {

	SLmillisecond mSec;
	SLresult result;

	if (NULL != fdPlayerPlay) {
		result = (*fdPlayerPlay)->GetDuration(fdPlayerPlay, (void*) &mSec);
		CheckErr(result);
		if (SL_TIME_UNKNOWN == result) {
			/*__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			 "NATIVE>> time unknown");*/
		} else {
			/*unsigned long res = mSec;
			 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			 "NATIVE>> time %lu", res);*/

			SLmillisecond now = NULL;
			int finished = 0;

			result = (*fdPlayerSeek)->SetLoop(fdPlayerSeek, SL_BOOLEAN_FALSE, 0,
						mSec);
				CheckErr(result);

			while (!finished) {
				result = (*fdPlayerPlay)->GetPosition(fdPlayerPlay,
						(void*) &now);
				CheckErr(result);
				if (now >= mSec) {
					finished = 1;
					fdPlayerPlay = NULL;
				}
			}
		}
	}
	return mSec;
}

/*
 * CREATE ASSET AUDIO PLAYER
 */
jboolean Java_com_example_customviewcircles_nativeaudio_OpenSLES_createAssetAudioPlayer(
		JNIEnv* env, jclass clazz, jobject assetManager, jstring filename) {

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE try play track");

	SLresult result;
	// convert Java string to UTF-8
	const char *utf8 = (*env)->GetStringUTFChars(env, filename, NULL);
	assert(NULL != utf8);

	// use asset manager to open asset by filename
	AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
	assert(NULL != mgr);
	AAsset* asset = AAssetManager_open(mgr, utf8, AASSET_MODE_UNKNOWN);

	// release the Java string and UTF-8
	(*env)->ReleaseStringUTFChars(env, filename, utf8);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE file %s", utf8);

	// the asset might not be found
	if (NULL == asset) {
		return JNI_FALSE;
	}

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE we got asset manager");

	// open asset as file descriptor
	off_t start, length;
	int fd = AAsset_openFileDescriptor(asset, &start, &length);
	assert(0 <= fd);
	AAsset_close(asset);

	// configure audio source
	// locator
	SLDataLocator_AndroidFD loc_fd = { SL_DATALOCATOR_ANDROIDFD, fd, start,
			length };
	// format
	SLchar* audioType = "audio/wav";
	SLDataFormat_MIME format_mime = { SL_DATAFORMAT_MIME, &audioType,
			SL_CONTAINERTYPE_WAV };
	// union locator + format
	SLDataSource audioSrc = { &loc_fd, &format_mime };

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE Audio source configured");

	// configure audio sink
	SLDataLocator_OutputMix loc_outmix = { SL_DATALOCATOR_OUTPUTMIX,
			outputMixObject };
	SLDataSink audioSnk = { &loc_outmix, NULL };

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE audio sink configured");

	// create audio player
	const SLInterfaceID ids[3] = { SL_IID_SEEK, SL_IID_VOLUME,
			SL_IID_PREFETCHSTATUS };
	const SLboolean req[3] =
			{ SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE };
	result = (*engineEngine)->CreateAudioPlayer(engineEngine, &fdPlayerObject,
			&audioSrc, &audioSnk, 3, ids, req);
	CheckErr(result);

	// realize the player
	result = (*fdPlayerObject)->Realize(fdPlayerObject, SL_BOOLEAN_FALSE);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE player realized");

	// get the play interface
	result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_PLAY,
			(void*) &fdPlayerPlay);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE 01");

	// get the seek interface
	result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_SEEK,
			(void*) &fdPlayerSeek);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE 02");

	// get the volume interface
	result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_VOLUME,
			(void*) &fdVolumeItf);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE 04");

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE all interface getted");

	// get the prefetch status interface
	result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
			SL_IID_PREFETCHSTATUS, (void*) &fdPrefetchStatusItf);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE get prefetc itf ok");

	// register callback on player object
	result = (*fdPrefetchStatusItf)->RegisterCallback(fdPrefetchStatusItf,
			GetDurationCallback, (void*) &fdPlayerPlay);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE register call back ok");

	// set callback event mask
	result = (*fdPrefetchStatusItf)->SetCallbackEventsMask(fdPrefetchStatusItf,
			SL_PREFETCHSTATUS_SUFFICIENTDATA);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE set callback event mask ok");

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE play track");

	// get dynamic interface
	result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
			SL_IID_DYNAMICINTERFACEMANAGEMENT,
			(void*) &dynamicInterfaceManagementItf);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE get dynamic interface");

	result = (*dynamicInterfaceManagementItf)->AddInterface(
			dynamicInterfaceManagementItf, SL_IID_PLAYBACKRATE,
			SL_BOOLEAN_FALSE);
	CheckErr(result);
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE add interface");

	/*SLpermille minPitch;
	 SLpermille maxPitch;
	 result = (*fdPitchItf)->GetPitchCapabilities(fdPitchItf, (void*) &minPitch,
	 (void*) &maxPitch);

	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE min %lu - max %lu", (unsigned long) &minPitch,
	 (unsigned long) &maxPitch);*/

	// add rate pitch itf
	/*result = (*dynamicInterfaceManagementItf)->AddInterface(
	 dynamicInterfaceManagementItf, SL_IID_RATEPITCH,
	 SL_BOOLEAN_FALSE);
	 CheckErr(result);
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE add rate pitch interface");*/

	// get the rate pitch interface
	/*result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
	 SL_IID_RATEPITCH,(void*)&fdSLRatePitchItf);
	 CheckErr(result);
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE rate pitch interface");*/

	setPlayingAssetAudioPlayer();

	return JNI_TRUE;
}

static SLVolumeItf getVolume() {
	if (fdVolumeItf != NULL)
		return fdVolumeItf;
}

void Java_com_example_customviewcircles_nativeaudio_OpenSLES_setVolume(
		JNIEnv* env, jclass clazz, jint mVolume) {
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE chiamato set volume %d", mVolume);

	SLresult result;
	SLVolumeItf volumeItf = getVolume();

	if (NULL != volumeItf) {

		long newVolume = (long)((long)SL_MILLIBEL_MIN/100)*mVolume;

		result = (*volumeItf)->SetVolumeLevel(volumeItf, -newVolume);
		CheckErr(result);

		/*SLmillibel maxVolume;
		 result = (*fdVolumeItf)->GetMaxVolumeLevel(fdVolumeItf,
		 (void*) &maxVolume);
		 CheckErr(result);
		 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		 ">>NATIVE min level %lu , maxLevel %lu",
		 (unsigned long) SL_MILLIBEL_MIN, (unsigned long) maxVolume);

		 SLmillibel minVolume = SL_MILLIBEL_MIN;
		 SLmillibel newVolume = minVolume
		 + (SLmillibel)(((float) (maxVolume - minVolume)) * mVolume);

		 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		 ">>NATIVE new level %lu", (unsigned long) newVolume);

		 result = (*fdVolumeItf)->SetVolumeLevel(fdVolumeItf, 0);
		 CheckErr(result);

		 SLmillibel level;
		 result = (*fdVolumeItf)->GetVolumeLevel(fdVolumeItf, (void*) &level);

		 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		 ">>NATIVE -> volume aggiornato %lu", (unsigned long) level);
		 } else {
		 __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE nulli");*/
	}
}
// shut down the native audio system
void Java_example_customviewcircles_nativeaudio_OpenSLES_shutdown(JNIEnv* env,
		jclass clazz) {

	// destroy file descriptor audio player object, and invalidate all associated interfaces
	if (fdPlayerObject != NULL) {
		(*fdPlayerObject)->Destroy(fdPlayerObject);
		fdPlayerObject = NULL;
		fdPlayerPlay = NULL;
		fdPlayerMuteSolo = NULL;
		fdPlayerSeek = NULL;
		dynamicInterfaceManagementItf = NULL;
		fdVolumeItf = NULL;
		fdPrefetchStatusItf = NULL;
		fdPlaybackRateItf = NULL;
		fdSLRatePitchItf = NULL;
	}

	// destroy output mix object, and invalidate all associated interfaces
	if (outputMixObject != NULL) {
		(*outputMixObject)->Destroy(outputMixObject);
		outputMixObject = NULL;
		outputMixEnvironmentalReverb = NULL;
	}

	// destroy engine object, and invalidate all associated interfaces
	if (engineObject != NULL) {
		(*engineObject)->Destroy(engineObject);
		engineObject = NULL;
		engineEngine = NULL;
	}

}
