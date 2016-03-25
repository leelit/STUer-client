package com.leelit.stuer.bean;

/**
 * Created by Leelit on 2016/3/25.
 */
public class TreeholeComment extends TreeholeInfo {

    boolean like;

    boolean unlike;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isUnlike() {
        return unlike;
    }

    public void setUnlike(boolean unlike) {
        this.unlike = unlike;
    }

    @Override
    public String toString() {
        return "TreeholeComment{" +
                "datetime='" + datetime + '\'' +
                ", state='" + state + '\'' +
                ", picAddress='" + picAddress + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                ", like=" + like +
                ", unlike=" + unlike +
                "} " + super.toString();
    }
}
