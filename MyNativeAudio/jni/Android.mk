LOCAL_PATH := $(call my-dir)

    include $(CLEAR_VARS)
    LOCAL_MODULE := opensles
    LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libOpenSLES.so
    LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
    LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
    include $(PREBUILT_SHARED_LIBRARY)