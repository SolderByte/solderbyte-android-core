package com.solderbyte.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broadcaster {

    private static final Map<String, List<Listener>> LISTENERS = new ConcurrentHashMap<>();

    public synchronized static void register(String event, Listener listener) {
        List<Listener> listeners = LISTENERS.get(event);
        if (listeners == null) {
            listeners = Collections.emptyList();
            LISTENERS.put(event, listeners);
        }
        listeners.add(listener);
    }

    public static void send(String event, Message message) {
        List<Listener> listeners = LISTENERS.get(event);
        if (listeners == null) {
            return;
        }
        for (Listener listener : listeners) {
            listener.onMessage(event, message);
        }
    }

    public interface Listener {
        void onMessage(String event, Message message);
    }

    public class Message {}
}
