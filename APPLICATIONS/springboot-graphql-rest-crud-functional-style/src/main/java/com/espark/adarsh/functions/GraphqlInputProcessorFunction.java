package com.espark.adarsh.functions;

@FunctionalInterface
public interface GraphqlInputProcessorFunction<T1, T2, R> {

    R processConversion(T1 t1, T2 t2);
}
