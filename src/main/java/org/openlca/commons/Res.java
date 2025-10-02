package org.openlca.commons;

import java.util.Objects;

public sealed interface Res<T> {

	T value();

	String error();

	default boolean isError() {
		return false;
	}

	default boolean isOk() {
		return true;
	}

	default boolean isEmpty() {
		return false;
	}

	public static <T> Res<T> ok(T value) {
		return new Ok<>(value);
	}

	public static Res<Void> ok() {
		return Empty.instance;
	}

	public static <T> Res<T> error(String message) {
		return new Err<>(message);
	}

	public static <T> Res<T> error(String message, Throwable err) {
		return err != null
				? error(message + ": " + err.getMessage())
				: error(message);
	}

	@SuppressWarnings("unchecked")
	default <E> Res<E> castError(String message) {
		return switch (this) {
			case Err<?> err -> (Res<E>) err;
			case Ok(Object value) -> error("Value casted to error: " + value);
			case Empty ignored -> error("Casted to error");
		};
	}

	default <E> Res<E> wrapError(String outerErr) {

	}

	record Ok<T>(T value) implements Res<T> {

		public Ok {
			Objects.requireNonNull(value);
		}

		@Override
		public String error() {
			throw new IllegalStateException("Result is not an error");
		}
	}

	record Empty() implements Res<Void> {

		private static final Empty instance = new Empty();

		@Override
		public Void value() {
			throw new IllegalStateException("Result is empty");
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public String error() {
			throw new IllegalStateException("Result is not an error");
		}
	}

	record Err<T>(String error) implements Res<T> {

		public Err {
			Objects.requireNonNull(error);
		}

		@Override
		public boolean isError() {
			return true;
		}

		@Override
		public boolean isOk() {
			return false;
		}

		@Override
		public T value() {
			throw new IllegalStateException("Result is an error: " + error);
		}
	}
}
