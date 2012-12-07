/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dorian
 */

public class test_Data extends Data implements Runnable{


    public static void main(String[] args) {
        Friend ffantasio = new Friend("fantasio", "?", "phonyIpf", 12);
        Friend fmario = new Friend("mario", "??", "phonyIpm", 13);
        Friend fsatanas = new Friend("satanas", "???", "phonyIps", 14);
        Friend[] AmiList = {ffantasio, fmario, fsatanas};

        Friend lucky_luck = new Friend("Lucky", "Luck", "Ipl", 15);

        Post mes1 = new Post("mario", "mamamia_!", "10/01/14_10:23:05");
        Post mes2 = new Post("Satanas", "Salut_les_poulettes_^^", "10/01/15_21:22:01");
        Post mes3 = new Post("visiteur", "HOHOHO", "10/01/15_21:27:51");
        Post[] postList = {mes1, mes2, mes3};

        User Fanta = new User("fantasio", "?-?", "12/12/12", AmiList, postList, "000.000.000", 1234);
        User Mario = new User("mario", "phonyipm", 1234);
        User Satanas = new User("mario", "phonyipm", 1234);

        System.out.print("messages\n");
        System.out.print(mes1.getPostString()+"\n");
        System.out.print(mes2.getPostString()+"\n");
        System.out.print(mes3.getPostString()+"\n");

        System.out.print("fantasio:\n"+
                   Fanta.getFirstName() + "\n" + Fanta.getLastName() + "\n" +
                   Fanta.getIp() + "\n");

        Flux FantaFlux = new Flux(Fanta.getPosts(), Fanta, "USER");
        Flux PostFlux = new Flux(postList, Fanta, "RE_SENDPOST");

        System.out.print(FantaFlux.FluxToString() + "\n");
        System.out.print(PostFlux.FluxToString() + "\n");

        LocalData.creerFile(FantaFlux, "Profile.xml");
        System.out.print("creation du fichier de profile de fantasio");
        User FantaUpdated = LocalData.FileToProfile("Profile.xml");
        System.out.print("verification:\n"+
                   FantaUpdated.getFirstName() + "\n" + FantaUpdated.getLastName() + "\n" +
                   FantaUpdated.getIp() + "\n");
        LocalData.ajouterAmi(lucky_luck, "Profile.xml");//bug
        FantaUpdated = LocalData.FileToProfile("Profile.xml"); //bug
        System.out.print("amis de fantasio :\n"+ FantaUpdated.getFriends() + "\n"); //bug

    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}