package io.github.kevvvvyp.springcloudawssample.transfer;

public enum Colour {
	GREEN("GREEN"),
	RED("RED"),
	WHITE("WHITE");

	String colour;
	Colour( final String colour ) {
		this.colour = colour;
	}
}
