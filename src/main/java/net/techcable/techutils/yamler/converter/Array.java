package net.techcable.techutils.yamler.converter;

import net.techcable.techutils.yamler.InternalConverter;

import java.lang.reflect.ParameterizedType;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Array implements Converter {
    private InternalConverter internalConverter;

    public Array(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        if ( type.isAssignableFrom( section.getClass() ) ) {
            return section;
        }

        java.util.List values = (java.util.List) section;
        return getArray(type, values);
    }

    private static <T> T[] getArray(Class<T> type, java.util.List list) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(type, list.size());
        return (T[]) list.toArray(array);
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.isArray();
    }
}
