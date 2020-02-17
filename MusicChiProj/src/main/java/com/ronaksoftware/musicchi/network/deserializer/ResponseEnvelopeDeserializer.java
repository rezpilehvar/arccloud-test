package com.ronaksoftware.musicchi.network.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ronaksoftware.musicchi.ApplicationLoader;
import com.ronaksoftware.musicchi.network.ResponseEnvelope;

import java.lang.reflect.Type;

//public class ResponseEnvelopeDeserializer implements JsonDeserializer<ResponseEnvelope> {
//    @Override
//    public ResponseEnvelope deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject jsonObject = json.getAsJsonObject();
//        ResponseEnvelope responseEnvelope = null;
//
//        if (jsonObject.has("constructor")) {
//            String constructor = jsonObject.get("constructor").getAsString();
//
//            if (constructor.equals("err")) {
//                responseEnvelope = new ResponseEnvelope();
//
//                JsonElement err = jsonObject.get("payload");
//
//                if (err != null) {
//                    responseEnvelope.error = err.getAsString();
//                }
//            } else {
//                responseEnvelope = ApplicationLoader.gson.fromJson(json, ResponseEnvelope.class);
//            }
//        }
//        return responseEnvelope;
//    }
//}
