package com.whoeveryou.FlameCryptoStuff;

class RayDES {
	
	public String encrypt(String plaintext) throws Exception 
	{
		return(mainProcess(plaintext,0));	
	}
	
	public String decrypt(String plaintext) throws Exception 
	{
		return(mainProcess(plaintext,1));	
	}
	
//Set Key	
	public void setKey(String str){
		key=str;
	}
//Main Process
	private static String mainProcess(String plain, int flag){
		String lr="";
		String[] subkey=getSubKey(key);
		String[] sout=new String[16];
		if (flag==1) 
			for (int i=1;i<9;i++){
				String substitute=subkey[i];
				subkey[i]=subkey[17-i];
				subkey[17-i]=substitute;
				}	
		String[] l=new String[17];
		String[] r=new String[17];
		String temp1="";
		String temp2="";
	//Apply IP		
		for (int i=0;i<64;i++)	lr+=plain.charAt(Ip[i]-1);
	//Get L0,R0
		l[0]=lr.substring(0,32);
		r[0]=lr.substring(32,64);
	//Get Li,Ri		
		for (int j=0;j<16;j++) {
			temp1="";
			sout[j]="";
			temp1=xorStr(Efun(r[j]),subkey[j+1]);
			
			l[j+1]=r[j];
		
			for (int i=0;i<8;i++)	sout[j]+=sProcess(temp1.substring(6*i,6*i+6),i+1);
			r[j+1]=xorStr(pProcess(sout[j]),l[j]);
		}
	//Get R16L16		
		temp2=r[16]+l[16];
		String cipher="";
		                
	//Apply IP Inverse		
		for (int i=0;i<64;i++)	cipher+=temp2.charAt(IpV[i]-1);	
		
		return cipher;
		
		
	}
			
// Function getSubKey	
	private static String[] getSubKey(String key){
		String[] keyarray={"","","","","","","","","","","","","","","","",""};
		String cd="";
		String c="";
		String d="";
		for (int i=0;i<56;i++)	cd+=key.charAt(PC1[i]-1);
		c=cd.substring(0,28);
		d=cd.substring(28,56);
		String[] carray={c,"","","","","","","","","","","","","","","",""};
		String[] darray={d,"","","","","","","","","","","","","","","",""};
		for (int j=1;j<17;j++){
			carray[j]=carray[j-1].substring(LS[j-1])+carray[j-1].substring(0,LS[j-1]);
			darray[j]=darray[j-1].substring(LS[j-1])+darray[j-1].substring(0,LS[j-1]);
			for (int i=0;i<48;i++)	keyarray[j]+=(carray[j]+darray[j]).charAt(PC2[i]-1);
		}
			
		return keyarray;
	}
	
// Function s Box Process	
	private static String sProcess(String instr,int snum){
		int[][][] s={s1,s1,s2,s3,s4,s5,s6,s7,s8};
		String str1=instr.substring(0,1)+instr.substring(5,6);
		String str2=instr.substring(1,5);
		int row=Integer.parseInt(str1,2);
		int col=Integer.parseInt(str2,2);
		String temp=Integer.toBinaryString(s[snum][row][col]);
		String str3="";
		for (int i=0;i<4-temp.length();i++) str3+="0";
		return str3+temp;
	}
	
//Function Efun	
	private static String Efun(String str){
		String Er="";
		for (int i=0;i<48;i++)	Er+=str.charAt(E[i]-1); 
		return Er;
		
	}
//Function notAnd
	private static String xorStr(String str1,String str2){
		String str="";
		for (int i=0;i<str1.length();i++) 
			str+=((str1.charAt(i)==str2.charAt(i))?"0":"1");
		return str;
	}
//Function P
	private static String pProcess(String str){
		String pstr="";
		for (int i=0;i<32;i++) pstr+=str.charAt(p[i]-1);
		return pstr;
	}
	
	public void DES(String str){
		setKey(str);
	}
	
	public void DES(){
	}

	
	private static int[] Ip={	58,50,42,34,26,18,10,2,
					60,52,44,36,28,20,12,4,
					62,54,46,38,30,22,14,6,
					64,56,48,40,32,24,16,8,
					57,49,41,33,25,17,9,1,
					59,51,43,35,27,19,11,3,
					61,53,45,37,29,21,13,5,
					63,55,47,39,31,23,15,7
				};
			
	private static int[] IpV={	40,8,48,16,56,24,64,32,
					39,7,47,15,55,23,63,31,
					38,6,46,14,54,22,62,30,
					37,5,45,13,53,21,61,29,
					36,4,44,12,52,20,60,28,
					35,3,43,11,51,19,59,27,
					34,2,42,10,50,18,58,26,
					33,1,41, 9,49,17,57,25
				};
	private static int[] E = {	32,1,2,3,4,5,
					4,5,6,7,8,9,
					8,9,10,11,12,13,
					12,13,14,15,16,17,
					16,17,18,19,20,21,
					20,21,22,23,24,25,
					24,25,26,27,28,29,
					28,29,30,31,32,1
				};
	private static int[][] s1={	{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
					{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
					{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
					{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
					};
	private static int[][] s2={	{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
					{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
					{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
					{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
					};
	private static int[][] s3={	{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
					{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
					{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
					{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
					};
	private static int[][] s4={	{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
					{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
					{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
					{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
					};
	private static int[][] s5={	{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
					{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
					{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
					{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
					};
	private static int[][] s6={	{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
					{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
					{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
					{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
					};
	private static int[][] s7={	{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
					{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
					{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
					{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
					};
	private static int[][] s8={	{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
					{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
					{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
					{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
					};
	private static int[] PC1={	57,49,41,33,25,17,9,
					1,58,50,42,34,26,18,
					10,2,59,51,43,35,27,
					19,11,3,60,52,44,36,
					63,55,47,39,31,23,15,
					7,62,54,46,38,30,22,
					14,6,61,53,45,37,29,
					21,13,5,28,20,12,4
				};
	private static int[] PC2 = {		14,17,11,24,1,5,
					3,28,15,6,21,10,
					23,19,12,4,26,8,
					16,7,27,20,13,2,
					41,52,31,37,47,55,
					30,40,51,45,33,48,
					44,49,39,56,34,53,
					46,42,50,36,29,32
				};
	private static int[] LS = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
	private static int[] p = {16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,
					2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};
	private static String key="";
	public static void main(String args[]) throws Exception 
	{
	
		RayDES d1=new RayDES();
		
		String key1=StrtoASCII("goodluck");
		d1.setKey(key1);
		String plaintext1="security";
		String plaintext=StrtoASCII(plaintext1);
		//String plaintext="0110100000110010100011010010010101001100110100000001100111100101";
		//String IV="0011000100110010001100110011010000110101001101100011011100111000";//"12345678"
		String cipher;
		System.out.println("明文："+plaintext);
		System.out.println("明文："+plaintext1);
		System.out.println("明文："+BtoH(plaintext));
		cipher=d1.encrypt(plaintext);
		System.out.println("密钥："+key1);
		System.out.println("密钥："+BtoH(key1));
		System.out.println("密文："+cipher);
		System.out.println("密文："+BtoH(cipher));
//		System.out.println("解密出的明文："+d1.decrypt(cipher));
		System.out.println("解密出的明文："+BtoH(d1.decrypt(cipher)));
		
	}
	
	private static String BtoH(String str){
		String h="";
		for (int j=0;j<16;j++) h+=Integer.toHexString(Integer.parseInt(str.substring(4*j,4*j+4),2));
		return h;
	}
	private static String StrtoASCII(String str){
		int len1=str.length();
		String h="";
		String h1="";
		for (int j=0;j<len1;j++) {
			h1=Integer.toBinaryString(str.charAt(j));
			int len2=8-h1.length();
			for(int i=0;i<len2;i++) h+="0";
			h+=h1;
			}
		return h;
	}
	


}