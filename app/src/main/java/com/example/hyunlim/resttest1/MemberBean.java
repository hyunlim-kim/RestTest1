package com.example.hyunlim.resttest1;

import com.example.hyunlim.resttest1.CommonBean;

import java.util.List;

/**
 * Created by qnqnqn1239 on 2017. 7. 21..
 */

public class MemberBean extends CommonBean {

    private MemberBeanSub memberBean;
    private List<MemberBeanSub> memberList;


    public class MemberBeanSub extends CommonBean {
        private String userId;
        private String userpw;
        private String name;
        private String addr;
        private String hp;
        private String profileImg;

        public String getProfileImg() {
            return profileImg;
        }

        public void setProfileImg(String profileImg) {
            this.profileImg = profileImg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserpw() {
            return userpw;
        }

        public void setUserpw(String userpw) {
            this.userpw = userpw;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getHp() {
            return hp;
        }

        public void setHp(String hp) {
            this.hp = hp;
        }
    }//end MembetBeanSub class

    public MemberBeanSub getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(MemberBeanSub memberBean) {
        this.memberBean = memberBean;
    }


    public List<MemberBeanSub> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberBeanSub> memberList) {
        this.memberList = memberList;
    }
}//end class

