LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES := com_example_firstnativeapp_FibLib.c
LOCAL_MODULE := com_example_firstnativeapp_FibLib
LOCAL_LDLIBS += -llog
include $(BUILD_SHARED_LIBRARY)