package com.ronaksoftware.musicchi.controllers;

import com.acrcloud.utils.ACRCloudExtrTool;

public class VoiceController {
    private static volatile VoiceController Instance;

    public static VoiceController getInstance() {
        VoiceController localInstance = Instance;
        if (localInstance == null) {
            synchronized (VoiceController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new VoiceController();
                }
            }
        }
        return localInstance;
    }

    private VoiceController() {

    }

    public void startRecording(){

    }

    public void stopRecording(){
        
    }


}
