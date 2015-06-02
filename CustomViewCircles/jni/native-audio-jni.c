#include <assert.h>
#include <jni.h>
#include <string.h>
#include <math.h>

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>

#include <sys/types.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>

//engine interfaces
static SLObjectItf engineObject = NULL;
static SLEngineItf engineEngine;

// output mix interfaces
static SLObjectItf outputMixObject = NULL;
static SLEnvironmentalReverbItf outputMixEnvironmentalReverb;

// file descriptor player interfaces
static SLObjectItf fdPlayerObject = NULL;
static SLPlayItf fdPlayerPlay;
static SLSeekItf fdPlayerSeek;
static SLVolumeItf fdVolumeItf;
static SLPrefetchStatusItf fdPrefetchStatusItf;
static SLDynamicInterfaceManagementItf dynamicInterfaceManagementItf;
static SLPlaybackRateItf fdPlaybackRateItf;

//------------------------------------------------------------------------------
//Needed Structs
//

typedef struct Players {
	SLObjectItf playerObject;
	SLPlayItf playerPlayItf;
	SLSeekItf playerSeekItf;
	SLVolumeItf playerVolumeItf;
	SLPrefetchStatusItf playerPrefetchStatusItf;
	SLDynamicInterfaceManagementItf playerDynamicInterfaceManagementItf;
	SLPlaybackRateItf playerPlaybackRateItf;
} Player;

//-----------------------------------------------------------------------------
//Needed Variables
//

Player playerObjects[9];

//----------------------------------------------------------------------------
//Create VM
//

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE Jni OnLoad");
	return JNI_VERSION_1_6;
}

//----------------------------------------------------------------------------
// Check Err Function
//
void CheckErr(SLresult res) {
	if (SL_RESULT_SUCCESS != res) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --ERRORE--");
	}
	if (SL_RESULT_PARAMETER_INVALID == res) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE parameter invalid");
	}
}

//---------------------------------------------------------------------------
//Create engine
//

void Java_com_example_customviewcircles_nativeaudio_OpenSLES_createEngine(
		JNIEnv* env, jclass clazz) {

	SLresult result;
	// create engine
	result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
	CheckErr(result);

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

	//get environment reverb interface
	result = (*outputMixObject)->GetInterface(outputMixObject,
			SL_IID_ENVIRONMENTALREVERB, &outputMixEnvironmentalReverb);
	CheckErr(result);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE SL_IID_ENVIRONMENTALREVERB ok");
}

void playerFinished(SLPlayItf caller, void *pContext, SLuint32 event) {
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE song finished");
}

//---------------------------------------------------------------------------
//playerReady callback
//
int isReady;

void playerReady(SLPlayItf caller, void *pContext, SLuint32 event) {
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE player ready %p",
			pContext);

	isReady = 1; //isReady is true

	SLresult result;
	SLmillisecond mSec;

	result = (*fdPlayerPlay)->GetDuration(fdPlayerPlay, (void*) &mSec);

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE duration %lu",
			(unsigned long) mSec);
}

Player createPlayer(JNIEnv* env, jobject assetManager, jstring filename) {
	__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE chiamato create player ");
	if (NULL != engineEngine) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE engine not null ");
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
			//return JNI_FALSE;
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

		Player temp;
		temp.playerObject = NULL;
		temp.playerPlayItf = NULL;
		temp.playerSeekItf = NULL;
		temp.playerVolumeItf = NULL;
		temp.playerPrefetchStatusItf = NULL;
		temp.playerDynamicInterfaceManagementItf = NULL;
		temp.playerPlaybackRateItf = NULL;

		Player* pTemp = &temp;

		// create audio player
		const SLInterfaceID ids[3] = { SL_IID_SEEK, SL_IID_VOLUME,
				SL_IID_PREFETCHSTATUS };
		const SLboolean req[3] = { SL_BOOLEAN_TRUE };
		result = (*engineEngine)->CreateAudioPlayer(engineEngine,
				(*(pTemp)->playerObject), &audioSrc, &audioSnk, 3, ids, req);
		CheckErr(result);

		// realize the player
		result = (*(pTemp)->playerObject)->Realize(pTemp->playerObject,
				SL_BOOLEAN_FALSE);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE player realized %p", &fdPlayerObject);

		// get the play interface
		result = (*temp.playerObject)->GetInterface(fdPlayerObject, SL_IID_PLAY,
				(void*) &temp.playerPlayItf);
		CheckErr(result);
		//__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get play itf");

		(*temp.playerPlayItf)->RegisterCallback(temp.playerPlayItf, playerFinished,
				(void*) &temp.playerPlayItf);
		CheckErr(result);

		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE callback registered");

		(*temp.playerPlayItf)->SetCallbackEventsMask(temp.playerPlayItf,
				SL_PLAYEVENT_HEADATEND);
		CheckErr(result);

		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE get callback mask");

		// get the volume interface
		result = (*temp.playerObject)->GetInterface(temp.playerObject, SL_IID_VOLUME,
				(void*) &temp.playerVolumeItf);
		CheckErr(result);
		//__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get volume itf");

		// get the seek interface
		result = (*temp.playerObject)->GetInterface(temp.playerObject, SL_IID_SEEK,
				(void*) &temp.playerSeekItf);
		CheckErr(result);
		//__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get seek itf");

		// get the prefetch status interface
		result = (*temp.playerObject)->GetInterface(temp.playerObject,
				SL_IID_PREFETCHSTATUS, (void*) &temp.playerPrefetchStatusItf);
		CheckErr(result);
		//__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		//	">>NATIVE get prefetc itf");

		// register callback on player object
		result = (*temp.playerPrefetchStatusItf)->RegisterCallback(temp.playerPrefetchStatusItf,
				playerReady, (void*) &temp.playerPrefetchStatusItf);
		CheckErr(result);
		//__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
		//	">>NATIVE register call back ok");

		// set callback event mask
		result = (*temp.playerPrefetchStatusItf)->SetCallbackEventsMask(
				temp.playerPrefetchStatusItf, SL_PREFETCHSTATUS_SUFFICIENTDATA);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE set callback event mask on player %p, itf %p",
				*temp.playerObject, *temp.playerPlayItf);

		// get dynamic interface
		result = (*temp.playerObject)->GetInterface(temp.playerObject,
				SL_IID_DYNAMICINTERFACEMANAGEMENT,
				(void*) &temp.playerDynamicInterfaceManagementItf);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE get dynamic interface");

		result = (*temp.playerDynamicInterfaceManagementItf)->AddInterface(
				temp.playerDynamicInterfaceManagementItf, SL_IID_PLAYBACKRATE,
				SL_BOOLEAN_FALSE);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE add playbackrate interface");

		result = (*temp.playerObject)->GetInterface(temp.playerObject,
				SL_IID_PLAYBACKRATE, &temp.playerPlaybackRateItf);
		CheckErr(result);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE get playbackrate interface");

		SLpermille minRate;
		SLpermille maxRate;
		SLpermille stepSize;
		SLuint32 capabilities;

		result = (*temp.playerPlaybackRateItf)->GetRateRange(temp.playerPlaybackRateItf, 0,
				(void*) &minRate, (void*) &maxRate, (void*) &stepSize,
				(void*) &capabilities);
		CheckErr(result);

		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
						">>NATIVE returning new player");

		return temp;
	}
}

//--------------------------------------------------------------------------
//set volume
//
void Java_com_example_customviewcircles_nativeaudio_OpenSLES_setVolume(
		JNIEnv* env, jclass clazz, jint level) {

	__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
			">>NATIVE chiamato seti volume");

	SLresult result;

	if (NULL != fdPlayerPlay) {

		double min = (double) -SL_MILLIBEL_MIN;
		int x = (int) 10 * log10(min);

		__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE x is %d", x);

		SLmillibel level;

		result = (*fdVolumeItf)->GetVolumeLevel(fdVolumeItf, (void*) &level);
		CheckErr(result);

		result = (*fdVolumeItf)->SetVolumeLevel(fdVolumeItf, level - 100);
		CheckErr(result);
	}
}

//---------------------------------------------------------------------------
//setRate
//
#define MINRATE 500
#define MAXRATE 2000

void Java_com_example_customviewcircles_nativeaudio_OpenSLES_setNewRate(
		JNIEnv* env, jclass clazz, jint rate) {

	SLresult result;

	int mRate = (int) rate;

	if (isReady) {

		int mUnity = (2000 - 500) / 100;

		int newRate = mUnity * mRate;

		SLpermille rate;

		result = (*fdPlaybackRateItf)->SetRate(fdPlaybackRateItf,
				mUnity * mRate);
		CheckErr(result);
		result = (*fdPlaybackRateItf)->GetRate(fdPlaybackRateItf,
				(void*) &rate);
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE mrate is %d, unity is %d , new rate is %d , rate is %d",
				mRate, mUnity, newRate, (int) rate);
		CheckErr(result);
	}
}

//---------------------------------------------------------------------------
//play sample
//
void playAssetAudioPlayer(SLPlayItf *playInterface) {

//__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE play song init. Pointer %p", playIterface);

	SLresult result;
	SLPlayItf playItf = (*playInterface);

	if (NULL != playItf) {
		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE context != null");

		(*playItf)->SetPlayState(playItf, SL_PLAYSTATE_PLAYING);
		CheckErr(result);

		isReady = 0; //isReady is false

		__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
				">>NATIVE play song end");
	}
}

//---------------------------------------------------------------------------
//Create asset audio player
//
jboolean Java_com_example_customviewcircles_nativeaudio_OpenSLES_createAssetAudioPlayer(
		JNIEnv* env, jclass clazz, jobject assetManager, jstring filename) {
	SLresult result;

	/*
	 // convert Java string to UTF-8
	 const char *utf8 = (*env)->GetStringUTFChars(env, filename, NULL);
	 assert(NULL != utf8);

	 // use asset manager to open asset by filename
	 AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
	 assert(NULL != mgr);
	 AAsset* asset = AAssetManager_open(mgr, utf8, AASSET_MODE_UNKNOWN);

	 // release the Java string and UTF-8
	 (*env)->ReleaseStringUTFChars(env, filename, utf8);

	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE file %s", utf8);

	 // the asset might not be found
	 if (NULL == asset) {
	 return JNI_FALSE;
	 }

	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 //	">>NATIVE we got asset manager");

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

	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 //	">>NATIVE Audio source configured");

	 // configure audio sink
	 SLDataLocator_OutputMix loc_outmix = { SL_DATALOCATOR_OUTPUTMIX,
	 outputMixObject };
	 SLDataSink audioSnk = { &loc_outmix, NULL };

	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 //	">>NATIVE audio sink configured");

	 // create audio player
	 const SLInterfaceID ids[3] = { SL_IID_SEEK, SL_IID_VOLUME,
	 SL_IID_PREFETCHSTATUS };
	 const SLboolean req[3] = { SL_BOOLEAN_TRUE };
	 result = (*engineEngine)->CreateAudioPlayer(engineEngine, &fdPlayerObject,
	 &audioSrc, &audioSnk, 3, ids, req);
	 CheckErr(result);

	 // realize the player
	 result = (*fdPlayerObject)->Realize(fdPlayerObject, SL_BOOLEAN_FALSE);
	 CheckErr(result);
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE player realized %p", &fdPlayerObject);

	 // get the play interface
	 result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_PLAY,
	 (void*) &fdPlayerPlay);
	 CheckErr(result);
	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get play itf");

	 (*fdPlayerPlay)->RegisterCallback(fdPlayerPlay, playerFinished,
	 (void*) &fdPlayerPlay);
	 CheckErr(result);

	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE callback registered");

	 (*fdPlayerPlay)->SetCallbackEventsMask(fdPlayerPlay,
	 SL_PLAYEVENT_HEADATEND);
	 CheckErr(result);

	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE get callback mask");

	 // get the volume interface
	 result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_VOLUME,
	 (void*) &fdVolumeItf);
	 CheckErr(result);
	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get volume itf");

	 // get the seek interface
	 result = (*fdPlayerObject)->GetInterface(fdPlayerObject, SL_IID_SEEK,
	 (void*) &fdPlayerSeek);
	 CheckErr(result);
	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE get seek itf");

	 // get the prefetch status interface
	 result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
	 SL_IID_PREFETCHSTATUS, (void*) &fdPrefetchStatusItf);
	 CheckErr(result);
	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 //	">>NATIVE get prefetc itf");

	 // register callback on player object
	 result = (*fdPrefetchStatusItf)->RegisterCallback(fdPrefetchStatusItf,
	 playerReady, (void*) &fdPlayerPlay);
	 CheckErr(result);
	 //__android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 //	">>NATIVE register call back ok");

	 // set callback event mask
	 result = (*fdPrefetchStatusItf)->SetCallbackEventsMask(fdPrefetchStatusItf,
	 SL_PREFETCHSTATUS_SUFFICIENTDATA);
	 CheckErr(result);
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE set callback event mask on player %p, itf %p",
	 *fdPlayerObject, *fdPlayerPlay);

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
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE add playbackrate interface");

	 result = (*fdPlayerObject)->GetInterface(fdPlayerObject,
	 SL_IID_PLAYBACKRATE, &fdPlaybackRateItf);
	 CheckErr(result);
	 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
	 ">>NATIVE get playbackrate interface");

	 SLpermille minRate;
	 SLpermille maxRate;
	 SLpermille stepSize;
	 SLuint32 capabilities;

	 result = (*fdPlaybackRateItf)->GetRateRange(fdPlaybackRateItf, 0,
	 (void*) &minRate, (void*) &maxRate, (void*) &stepSize,
	 (void*) &capabilities);
	 CheckErr(result);
	 */

	Player first = createPlayer(env, assetManager, filename);
	playerObjects[0] = first;

	playAssetAudioPlayer(&first.playerPlayItf);
}

//---------------------------------------------------------------------------
//Destroy section
//

/*void destroyPlayer(SLPlayItf *playItf) {

 SLPlayItf player = (*playItf);

 if (player != NULL) {
 (*player)->Destroy(player);
 player = NULL;
 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
 ">>NATIVE player destroyed");
 }
 }

 void destroyOutputMixer() {
 if (outputMixObject != NULL) {
 (*outputMixObject)->Destroy(outputMixObject);
 outputMixObject = NULL;
 outputMixEnvironmentalReverb = NULL;
 }
 }

 void destroyEngine() {
 if (engineObject != NULL) {
 (*engineObject)->Destroy(engineObject);
 engineObject = NULL;
 engineEngine = NULL;
 }
 }

 void Java_com_example_customviewcircles_nativeaudio_OpenSLES_shutdownEngine(
 JNIEnv* env, jclass clazz) {
 destroyPlayer();
 destroyOutputMixer();
 destroyEngine();
 __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
 ">>NATIVE Tutto distrutto");
 }*/
