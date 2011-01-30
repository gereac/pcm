package com.gcsf.pcm.model;

import java.util.List;

public class UserGroup {

	private String groupName;

	private String groupDescription;

	private User groupOwner;

	private List<User> groupMembers;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public User getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(User groupOwner) {
		this.groupOwner = groupOwner;
	}

	public List<User> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<User> groupMembers) {
		this.groupMembers = groupMembers;
	}

}
