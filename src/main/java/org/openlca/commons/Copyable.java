package org.openlca.commons;

/// A simple interface for objects that can create copies of themselves.
/// This interface is typically preferred over `Cloneable` and `Object.clone()`
/// because it provides a public, type-safe copy method that doesn't require
/// handling `CloneNotSupportedException`.
public interface Copyable<T extends Copyable<T>> {

	/// Creates and returns a copy of this object. The exact meaning of "copy"
	/// may depend on the implementation, but typically means creating a new
	/// instance with the same state as this object.
	T copy();

}
