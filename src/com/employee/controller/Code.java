package com.employee.controller;

public class Code {
	 public String getRandomPassword() {
		    int z;
		    StringBuilder sb = new StringBuilder();
		    int i;
		    for (i = 0; i < 4; i++) {
		      z = (int) ((Math.random() * 7) % 3);
		 
		      if (z == 1) { // ��Ʀr
		        sb.append((int) ((Math.random() * 10) + 48));
		      } else if (z == 2) { // ��j�g�^��
		        sb.append((char) (((Math.random() * 26) + 65)));
		      } else {// ��p�g�^��
		        sb.append(((char) ((Math.random() * 26) + 97)));
		      }
		    }
		    return sb.toString();
		  }
	 public static void main(String[] args) {
		 Code c = new Code();
		 for(int i=0;i<1000;i++) {
			 System.out.println(c.getRandomPassword());
			 
		 }
		 
		 
		 
	 }
}
