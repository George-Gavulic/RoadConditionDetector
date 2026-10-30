package edu.oakland.roadconditiondetector;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventBus {
    private static EventBus instance;

    private final Set<EventListener> subscribers = new CopyOnWriteArraySet<>();

    private EventBus() {}

    public static synchronized EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void register(EventListener subscriber) {
        subscribers.add(subscriber);
    }

    public void unregister(EventListener subscriber) {
        subscribers.remove(subscriber);
    }

    public void post(Object event) {
        for (EventListener subscriber : subscribers) {
            subscriber.onEvent(event);
        }
    }

    public interface EventListener {
        void onEvent(Object event);
    }
}