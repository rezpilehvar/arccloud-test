package com.ronaksoftware.musicchi.controllers.recognizer;

import com.ronaksoftware.musicchi.network.ErrorResponse;
import com.ronaksoftware.musicchi.network.response.SoundSearchResponse;

public interface RecognizeResult {

    class Success implements RecognizeResult {
        private SoundSearchResponse soundSearchResponse;

        public Success(SoundSearchResponse soundSearchResponse) {
            this.soundSearchResponse = soundSearchResponse;
        }

        public SoundSearchResponse getSoundSearchResponse() {
            return soundSearchResponse;
        }
    }

    class Error implements RecognizeResult {
        private int errorCode;
        private String body;
        private Exception exception;

        public Error(int errorCode) {
            this.errorCode = errorCode;
        }

        public Error(String body) {
            this.body = body;
        }

        public Error(int errorCode, String body) {
            this.errorCode = errorCode;
            this.body = body;
        }

        public Error(int errorCode, Exception exception) {
            this.errorCode = errorCode;
            this.exception = exception;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getBody() {
            return body;
        }

        public Exception getException() {
            return exception;
        }
    }

    class ServerError implements RecognizeResult {
        private ErrorResponse error;

        public ServerError(ErrorResponse error) {
            this.error = error;
        }
    }
}
