public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + "=" + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11; // default capacity
    private int size;

    public MyHashTable() {
        this.chainArray = new HashNode[M];
    }

    public MyHashTable(int M) {
        this.M = M;
        this.chainArray = new HashNode[M];
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(K key, V value) {
        int hashIndex = hash(key);
        HashNode<K, V> head = chainArray[hashIndex];
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        size++;
        head = chainArray[hashIndex];
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = head;
        chainArray[hashIndex] = newNode;
    }

    public V get(K key) {
        int hashIndex = hash(key);
        for (HashNode<K, V> node = chainArray[hashIndex]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int hashIndex = hash(key);
        HashNode<K, V> previous = null;
        for (HashNode<K, V> node = chainArray[hashIndex]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                if (previous != null) {
                    previous.next = node.next;
                } else {
                    chainArray[hashIndex] = node.next;
                }
                size--;
                return node.value;
            }
            previous = node;
        }
        return null;
    }

    public boolean contains(V value) {
        for (HashNode<K, V> chain : chainArray) {
            for (HashNode<K, V> node = chain; node != null; node = node.next) {
                if (node.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (HashNode<K, V> chain : chainArray) {
            for (HashNode<K, V> node = chain; node != null; node = node.next) {
                if (node.value.equals(value)) {
                    return node.key;
                }
            }
        }
        return null;
    }
    public void printBucketSizes() {
        for (int i = 0; i < chainArray.length; i++) {
            int count = 0;
            for (HashNode<K, V> node = chainArray[i]; node != null; node = node.next) {
                count++;
            }
            System.out.println("Bucket " + i + " has " + count + " elements.");
        }
    }

}

