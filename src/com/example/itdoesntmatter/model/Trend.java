package com.example.itdoesntmatter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trend implements Parcelable{

	private String name;
	private boolean isPromoted;
	public String getName() {
		return name;
	}

	public boolean isPromoted() {
		return isPromoted;
	}
	
	public Trend(String name,boolean promoted){
		this.name = name;
		this.isPromoted = promoted;
	}

	@Override
	public int describeContents() {
		return name.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeByte((byte)(isPromoted?1:0));
		
	}
	
	public static final Parcelable.Creator<Trend> CREATOR
		    = new Parcelable.Creator<Trend>() {
		public Trend createFromParcel(Parcel in) {
		    return new Trend(in);
		}

	public Trend[] newArray(int size) {
	    return new Trend[size];
	}
	};
	
	public Trend(Parcel in) {
	  name = in.readString();
	  isPromoted = (int)in.readByte()==0?false:true;
	}
	public Trend(){
		
	}
}
