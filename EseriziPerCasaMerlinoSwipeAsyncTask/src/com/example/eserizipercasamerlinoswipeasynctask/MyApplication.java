package com.example.eserizipercasamerlinoswipeasynctask;

import java.util.Stack;

import android.app.Application;

public class MyApplication extends Application{

	public Stack<Integer> stackNumeri = new Stack<Integer>();
	public Stack<String> stackOperatori = new Stack<String>();
	public Stack<Integer> orderedNumbersStack = new Stack<Integer>();
	public Stack<String> orderedOperatorsStack = new Stack<String>();
}
