package com.leelit.stuer.bean;

import java.util.List;

/**
 * Created by Leelit on 2016/3/25.
 */
public class TreeholeComment {

    int likeCount;

    int unlikeCount;

    List<Comment> comments;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getUnlikeCount() {
        return unlikeCount;
    }

    public void setUnlikeCount(int unlikeCount) {
        this.unlikeCount = unlikeCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "TreeholeComment{" +
                "likeCount=" + likeCount +
                ", unlikeCount=" + unlikeCount +
                ", comments=" + comments +
                '}';
    }

    public static class Comment {
        String uniquecode;
        String name;
        String dt;
        String comment;
        String imei;

        public String getUniquecode() {
            return uniquecode;
        }

        public void setUniquecode(String uniquecode) {
            this.uniquecode = uniquecode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "uniquecode='" + uniquecode + '\'' +
                    ", name='" + name + '\'' +
                    ", dt='" + dt + '\'' +
                    ", comment='" + comment + '\'' +
                    ", imei='" + imei + '\'' +
                    '}';
        }
    }
}
