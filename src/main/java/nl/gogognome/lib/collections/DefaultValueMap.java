package nl.gogognome.lib.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class wraps an existing Map. All methods are redirected to the wrapped Map.
 * Only the get() method is changed. When the wrapped Map contains the request key then the corresponding value
 * is returned; otherwise the default value is returend.
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public class DefaultValueMap<K,V> implements Map<K,V> {

    private final Map<K,V> wrappedMap;

    private final V defaultValue;

    public DefaultValueMap(Map<K, V> wrappedMap, V defaultValue) {
        this.wrappedMap = wrappedMap;
        this.defaultValue = defaultValue;
    }

    @Override
    public int size() {
        return wrappedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return wrappedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return wrappedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return wrappedMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (wrappedMap.containsKey(key)) {
            return wrappedMap.get(key);
        } else {
            return defaultValue;
        }
    }

    @Override
    public V put(K key, V value) {
        return wrappedMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return wrappedMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        wrappedMap.putAll(m);
    }

    @Override
    public void clear() {
        wrappedMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return wrappedMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return wrappedMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return wrappedMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return wrappedMap.equals(o);
    }

    @Override
    public int hashCode() {
        return wrappedMap.hashCode();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return wrappedMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        wrappedMap.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        wrappedMap.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return wrappedMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return wrappedMap.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return wrappedMap.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return wrappedMap.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return wrappedMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return wrappedMap.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return wrappedMap.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return wrappedMap.merge(key, value, remappingFunction);
    }
}
