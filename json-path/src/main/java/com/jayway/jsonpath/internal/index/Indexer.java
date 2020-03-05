package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.Configuration;
import com.sun.tools.corba.se.idl.constExpr.Times;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Indexer {
    class Timestamp {
        private int timestamp;
        public Timestamp() {this.timestamp = 0;}

        public Timestamp inc() {
            this.timestamp ++;
            return this;
        }
        public int getTimestamp() {
            return this.timestamp;
        }
    }

    public IndexContext index(Object json, Configuration configuration) {
        Map<String, LinkedList<Node>> partitions = new HashMap<String, LinkedList<Node>>();
        iterateJsonTree("$", json, configuration, new Timestamp(), 0, partitions);
        return new IndexContext();
    }

    private void iterateJsonTree(String key,
                                 Object json,
                                 Configuration configuration,
                                 Timestamp timestamp,
                                 int level,
                                 Map<String, LinkedList<Node>> partitions) {
        int firstVisit = timestamp.getTimestamp();
        if (configuration.jsonProvider().isMap(json)) {
            for (String property: configuration.jsonProvider().getPropertyKeys(json)) {
                iterateJsonTree(
                        property,
                        configuration.jsonProvider().getMapValue(json, property),
                        configuration,
                        timestamp.inc(),
                        level + 1,
                        partitions);
            }
        } else if (configuration.jsonProvider().isArray(json)) {
            for (Object obj: configuration.jsonProvider().toIterable(json)) {
                iterateJsonTree(
                        key,
                        obj,
                        configuration,
                        timestamp.inc(),
                        level,
                        partitions);
            }
        }
        int lastVisit = timestamp.getTimestamp();
        if (!partitions.containsKey(key)) partitions.put(key, new LinkedList<Node>());
        partitions.get(key).add(new Node(firstVisit, lastVisit, level, key, json));
    }
}
