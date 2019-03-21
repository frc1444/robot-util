package com.first1444.robot.util.valuemap;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;

class ImmutableValueMap<T extends Enum<T> & ValueKey> extends ValueMapBase<T>{

	public ImmutableValueMap(Class<T> clazz, EnumMap<T, Object> enumMap){
		super(Collections.unmodifiableMap(enumMap.clone()), Collections.unmodifiableSet(EnumSet.allOf(clazz)));
	}
}
