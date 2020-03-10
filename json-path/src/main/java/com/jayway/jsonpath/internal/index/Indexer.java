package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.Configuration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Indexer {
    static class Timestamp {
        private long timestamp;
        public Timestamp() {this.timestamp = 0;}

        public Timestamp inc() {
            this.timestamp ++;
            return this;
        }
        public long getTimestamp() {
            return this.timestamp;
        }
    }

    public static IndexContext index(Object json, Configuration configuration) {
        Map<String, LinkedList<Node>> objectsPartitions = new HashMap<String, LinkedList<Node>>();
        Map<Long, LinkedList<Node>> arraysPartitions = new HashMap<Long, LinkedList<Node>>();
        iterateJsonObject("$", json, configuration, new Timestamp(), 0, objectsPartitions, arraysPartitions);
        return new IndexContext(objectsPartitions, arraysPartitions);
    }

    private static void iterateJsonObject(String key,
                                          Object json,
                                          Configuration configuration,
                                          Timestamp timestamp,
                                          int level,
                                          Map<String, LinkedList<Node>> objectsPartitions,
                                          Map<Long, LinkedList<Node>> arraysPartitions) {
        ObjectNode node = new ObjectNode(key, json);
        node.setFirstVisit(timestamp.getTimestamp()); timestamp.inc();
        node.setLevel(level);
        if (!objectsPartitions.containsKey(key)) objectsPartitions.put(key, new LinkedList<Node>());
        objectsPartitions.get(key).add(node);
        iterateJson(json, configuration, timestamp, level, objectsPartitions, arraysPartitions);
        node.setLastVisit(timestamp.getTimestamp()); timestamp.inc();
    }

    private static void iterateJsonArray(long index,
                                         Object json,
                                         Configuration configuration,
                                         Timestamp timestamp,
                                         int level,
                                         Map<String, LinkedList<Node>> objectsPartitions,
                                         Map<Long, LinkedList<Node>> arraysPartitions) {
        ArrayNode node = new ArrayNode(index, json);
        node.setFirstVisit(timestamp.getTimestamp()); timestamp.inc();
        node.setLevel(level);
        if (!arraysPartitions.containsKey(index)) arraysPartitions.put(index, new LinkedList<Node>());
        arraysPartitions.get(index).add(node);
        iterateJson(json, configuration, timestamp, level, objectsPartitions, arraysPartitions);
        node.setLastVisit(timestamp.getTimestamp()); timestamp.inc();
    }

    private static void iterateJson(
            Object json,
            Configuration configuration,
            Timestamp timestamp,
            int level,
            Map<String, LinkedList<Node>> objectsPartitions,
            Map<Long, LinkedList<Node>> arraysPartitions) {
        if (configuration.jsonProvider().isMap(json)) {
            for (String property: configuration.jsonProvider().getPropertyKeys(json)) {
                iterateJsonObject(
                        property,
                        configuration.jsonProvider().getMapValue(json, property),
                        configuration,
                        timestamp,
                        level + 1,
                        objectsPartitions,
                        arraysPartitions);
            }
        } else if (configuration.jsonProvider().isArray(json)) {
            long index = 0;
            for (Object obj: configuration.jsonProvider().toIterable(json)) {
                iterateJsonArray(
                        index,
                        obj,
                        configuration,
                        timestamp,
                        level + 1,
                        objectsPartitions,
                        arraysPartitions);
                index ++;
            }
        }
    }
}
