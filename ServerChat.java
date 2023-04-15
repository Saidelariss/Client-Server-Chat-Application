import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerChat {
    public static void main(String[] args) {
    
        ThreadServeur threadServeur = new ThreadServeur(Integer.parseInt(args[0]));
        threadServeur.start();

        ThreadClient threadClient = new ThreadClient(args[1], Integer.parseInt(args[2]) );
        threadClient.start();

        

    }


}

class ThreadServeur extends Thread{
    int NumPort;
    public ThreadServeur(int NumPort){
        this.NumPort = NumPort;
    }
    public void run(){
        try{
        ServerSocket SS = new ServerSocket(NumPort);
        System.out.println("en attente de connexion ...");
        Socket S = SS.accept();
        System.out.println("Connexion établie");
        //Ouvrir un flux de lecture sur la socket
        BufferedReader BR = new BufferedReader(new InputStreamReader(S.getInputStream()));
        //Ouvrir un flux d’écriture sur la socket
        PrintWriter PW = new PrintWriter(S.getOutputStream());
        //Réaliser les traitements
        //Communication à travers les flux de lecture et d’écriture
        String s = BR.readLine();
        while(!(s.equals("Fin"))){

            PW.println("serveur : bien reçu");
            PW.flush();
            System.out.println("client : "+s);
            s = BR.readLine();

        }


        }catch(Exception e){}
    }
    
}

class ThreadClient extends Thread{
        int NumPort;
        String ipAdress;

        public ThreadClient(String ipAdress,int NumPort){
            this.NumPort=NumPort;
            this.ipAdress=ipAdress;
        }
        public void run(){
            Socket S = new Socket();
            try{
                System.out.println("Connexion en cours ...");
                Boolean connected = false;
                while(!connected){
                    try{
                        connected = true;
                        S=new Socket(ipAdress,NumPort);
                    }catch(Exception e){
                        connected=false;
                    }
                }
        System.out.println("La connexion est établie");
        //Ouvrir un flux de lecture sur la socket
        BufferedReader BR = new BufferedReader(new InputStreamReader(S.getInputStream()));
        //Ouvrir un flux d’écriture sur la socket
        PrintWriter PW = new PrintWriter( S.getOutputStream());
        //Réaliser les traitements
        //Communication à travers les flux de lecture et d’écriture
        // System.out.println(BR.readLine());
        Scanner sc = new Scanner(System.in);
        String ss = sc.nextLine();
        System.out.println("ecrivez moi un message svp :  ");
        String name = new String();
        while(!(name.equals("Fin"))){
            System.out.print("Client :  ");
            name = sc.nextLine();
            PW.println(name);
            PW.flush();
             System.out.println(BR.readLine());
         }
            }catch(Exception e){e.getMessage();}

        }

}
