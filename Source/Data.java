import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.*;

/**
 *
 * @author dorian
 */
public abstract class Data {
}

class User extends Data {

    private String firstName;
    private String lastName;
    private String birthday;
    private Friend[] friends;
    private Post[] posts;
    private String ip;
    private int port;

    public User(){
        this("?", "?", 1234);
    }

    public User(String firstName, String ip, int port) {
        this(firstName, "?", "?", null, null, ip, port);
    }

    public User(String firstName, String lastName, String birthday, Friend[] friends, Post[] post, String ip, int port) {
        this.firstName = firstName;
        this.ip = ip;
        this.port = port;
        this.lastName = lastName;
        this.birthday = birthday;
        this.friends = friends;
        this.posts = post;
    }

    public String getFirstName() {
        String s = this.firstName;
        return s;
    }
    public void setfirstName(String f){
        this.firstName = f;
    }

    public String getLastName() {
        String s = this.lastName;
        return s;
    }
    public void setlastName(String l){
        this.lastName = l;
    }

    public String getBirthday() {
        String s = this.birthday;
        return s;
    }
    public void setBirthday(String b){
        this.birthday = b;
    }

    public Friend[] getFriends() {
        Friend[] f = new Friend[this.friends.length + 1];
        System.arraycopy(this.friends, 0, f, 0, this.friends.length);
        return f;
    }
    public void addFriend(Friend f){
        Friend[] newf = new Friend[this.friends.length + 1];
        System.arraycopy(this.friends, 0, newf, 0, this.friends.length);
        newf[this.friends.length + 1] = f;
    }

    public Post[] getPosts() {
        Post[] p = new Post[this.posts.length + 1];
        System.arraycopy(this.posts, 0, p, 0, this.posts.length);
        return p;
    }
    public void addPost(Post p){
        Post[] newp = new Post[this.posts.length + 1];
        System.arraycopy(this.posts, 0, newp, 0, this.posts.length);
        newp[this.posts.length + 1] = p;
    }

    public String getIp() {
        String s = this.ip;
        return s;
    }
    public void setIp(String ip){
        this.ip = ip;
    }
    public String getPort() {
        String s = Integer.toString(this.port);
        return s;
    }
    public void setPort(int port){
        this.port = port;
    }
}

class Friend extends Data{
    private String firstName;
    private String lastName;
    private String ip;
    private int cle;

    public Friend(String firstName, String lastName, String ip, int cle){
        this.firstName = firstName;
        this.lastName = lastName;
        this.ip = ip;
        this.cle = cle;
    }

    public String getFirstName(){
        String f = new String();
        f = this.firstName;
        return f;
    }

    public String getLastName() {
        String l = new String();
        l = this.lastName;
        return l;
    }

    public String getIp() {
        String i = new String();
        i = this.ip;
        return i;
    }
    public int getCle(){
        return this.cle;
    }
}

class Post extends Data {

    private String author;
    private String content;
    private String date;

    public Post(String author, String content, String Date) {
        this.author = author;
        this.content = content;
        this.date = Date;
    }

    public String getPostString() {
        StringBuilder s = new StringBuilder();
        s.append(this.author);
        s.append(':');
        s.append(this.date);
        s.append(':');
        s.append(this.content);
        return s.toString();
    }

    public String getAuthor() {
        String s = this.author;
        return s;
    }

    public String getContent() {
        String s = this.content;
        return s;
    }

    public String getDate() {
        String s = this.date;
        return s;
    }
}

class Flux extends Data {

    private String donnee;

    /**
     * cree un flux en format xml
     *
     * @param type de flux a creer
     * @param p post a envoyer
     * @param u personne qui envoit le post
     */
    public Flux(Post[] p, User u, String type) {
        //XMLStreamWriter writer = null;
        XMLOutputFactory outFact = XMLOutputFactory.newInstance();
        ByteArrayOutputStream outStr = new ByteArrayOutputStream();
        try {
            XMLStreamWriter writer = outFact.createXMLStreamWriter(outStr, "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");

            if (type.equals("RE_SENDPOST")) {
                writer.writeStartElement("RESPONSE");
                writer.writeEndElement();

                writer.writeStartElement("KEYWORD");
                writer.writeComment("SENDPOST");
                writer.writeEndElement();

                writer.writeStartElement("BODY");

                writer.writeStartElement("OWNER");
                writer.writeComment(p[0].getAuthor());
                writer.writeEndElement();

                writer.writeStartElement("ID");
                writer.writeComment(u.getIp());
                writer.writeEndElement();

                writer.writeStartElement("DATE");
                writer.writeComment(p[0].getDate());
                writer.writeEndElement();

                writer.writeStartElement("CONTENT");
                writer.writeComment(p[0].getContent());
                writer.writeEndElement();

                //writer.writeEndElement(); //BODY
                //writer.writeEndElement(); //RESPONSE
            } else if (type.equals("USER")) {
                //writer.writeStartElement("Profile");

                writer.writeStartElement("firstName");
                writer.writeComment(u.getFirstName());
                writer.writeEndElement();

                writer.writeStartElement("lastName");
                writer.writeComment(u.getLastName());
                writer.writeEndElement();

                writer.writeStartElement("birthday");
                writer.writeComment(u.getBirthday());
                writer.writeEndElement();

                writer.writeStartElement("ip");
                writer.writeComment(u.getIp());
                writer.writeEndElement();

                writer.writeStartElement("port");
                writer.writeComment(u.getPort());
                writer.writeEndElement();

                setFluxPosts(writer, p);
                setFluxFriends(writer, u);

                //writer.writeEndElement(); //profile
            } else if (type.equals("RE_LISTPOST")) {
                writer.writeStartElement("RESPONSE");

                writer.writeStartElement("CODE");//code de retour en fonction des info envoyees
                writer.writeComment("202");//pour l'instant par defaut 202
                writer.writeEndElement();

                setFluxPosts(writer, p);
                writer.writeEndElement();
            } else if (type.equals("RE_WHO")) {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        this.donnee = outStr.toString();
    }

    public Flux(String s) {
        this.donnee =new String(s);
    }

    public User FluxToUser() {
        XMLInputFactory inFact = XMLInputFactory.newInstance();
        User u = new User();
        try {
            XMLStreamReader reader = inFact.createXMLStreamReader(new StringReader(this.donnee));
            PrintWriter pw = new PrintWriter(System.out, true);
            while (reader.hasNext()) {
                if (reader.isStartElement()) {
                    if (reader.getLocalName().equals("firstName")) {
                        u.setfirstName(reader.getElementText());
                    }
                    if (reader.getLocalName().equals("lastName")) {
                        u.setlastName(reader.getElementText());
                    }
                    if (reader.getLocalName().equals("birthday")) {
                        u.setBirthday(reader.getElementText());
                    }
                    if (reader.getLocalName().equals("ip")) {
                        u.setIp(reader.getElementText());
                    }
                    if (reader.getLocalName().equals("port")) {
                        u.setPort(Integer.getInteger(reader.getElementText()));
                    }
                    if (reader.getLocalName().equals("POST")) {
                        String last = reader.getElementText();
                        reader.next();
                        String first = reader.getElementText();
                        reader.next();
                        String ip = reader.getElementText();

                        Friend f = new Friend(first, last, ip, 1);
                        u.addFriend(f);
                    }

                    if (reader.getLocalName().equals("friends")) {
                        String author = reader.getElementText();
                        reader.next();
                        String date = reader.getElementText();
                        reader.next();
                        String content = reader.getElementText();
                        reader.next();

                        Post p = new Post(author, content, date);
                        u.addPost(p);
                    }
                }
                reader.next();
            }
        } catch (Exception e) {
        }
        return u;
    }
    public String FluxToString(){
        String s = new String();
        s = this.donnee;
        return s;

    }

    /**
     * creer un tableau de donnees a partir d'un flux xml
     *
     * @return la premiere case indique le type de donnee recue
     */
    public String[] FluxToStringArray() {
        XMLInputFactory inFact = XMLInputFactory.newInstance();
        String out[] = {"vide"};
        try {
            XMLStreamReader reader = inFact.createXMLStreamReader(new StringReader(this.donnee));
            PrintWriter pw = new PrintWriter(System.out, true);

            while (reader.hasNext()) {
                if (reader.isStartElement()) {
                    if (reader.getLocalName().equals("KEYWORD")) {

                        if (reader.getElementText().equals("SENDPOST")) {
                            out = new String[5];
                            out[0] = "SENDPOST";
                            while (reader.hasNext()) {
                                if (reader.getLocalName().equals("OWNER")) {
                                    out[1] = reader.getElementText();
                                }
                                if (reader.getLocalName().equals("ID")) {
                                    out[2] = reader.getElementText();
                                }
                                if (reader.getLocalName().equals("DATE")) {
                                    out[3] = reader.getElementText();
                                }
                                if (reader.getLocalName().equals("CONTENT")) {
                                    out[4] = reader.getElementText();
                                }
                                reader.next();
                            }
                        }
                    }else if (reader.getLocalName().equals("KEYWORD")){
                        out = new String[8];
                        out[0] = "USER";
                        while (reader.hasNext()){
                            if (reader.getLocalName().equals("firtName"))
                                out[1] = reader.getElementText();
                            if (reader.getLocalName().equals("lastName"))
                                out[2] = reader.getElementText();
                            if (reader.getLocalName().equals("birthdayName"))
                                out[3] = reader.getElementText();
                            if (reader.getLocalName().equals("friends")){
                                //
                            }
                            if (reader.getLocalName().equals("posts")){
                                //
                            }
                            if (reader.getLocalName().equals("ip"))
                                out[6] = reader.getElementText();
                            if (reader.getLocalName().equals("port"))
                                out[7] = reader.getElementText();
                            reader.next();
                        }

                    }


                }
                reader.next();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return out;
    }

    /**
     * ecrit les posts de u dans writer
     *
     * @param writer du flux
     * @param u
     */
    public static void setFluxPosts(XMLStreamWriter writer, Post[] p) {
        try {
            for (int i = 0; i <= p.length; i++) {
                writer.writeStartElement("POST");
                writer.writeEndElement();

                writer.writeStartElement("author");
                writer.writeComment(p[i].getAuthor());
                writer.writeEndElement();

                writer.writeStartElement("date");
                writer.writeComment(p[i].getDate());
                writer.writeEndElement();

                writer.writeStartElement("content");
                writer.writeComment(p[i].getContent());
                writer.writeEndElement();

                //writer.writeEndElement();
            }
        } catch (Exception e) {
        }
    }
    /**
     * ecrit les amis de u dans writer
     *
     * @param writer du flux
     * @param u
     */
    public static void setFluxFriends(XMLStreamWriter writer, User u) {
        Friend[] f = new Friend[u.getFriends().length];
        System.arraycopy(u.getFriends(), 0, f, 0, u.getFriends().length);
        try {
            for (int i = 0; i <= f.length; i++) {
                writer.writeStartElement("friend");
                writer.writeEndElement();

                writer.writeStartElement("name");
                writer.writeComment(f[i].getFirstName());
                writer.writeComment(f[i].getLastName());
                writer.writeEndElement();

                writer.writeStartElement("ip");
                writer.writeComment(f[i].getIp());
                writer.writeEndElement();

                //writer.writeEndElement();
            }
        } catch (Exception e) {
        }
    }
}

/**
 *
 *donnees stockees en local
 */
class LocalData extends Data {

    /**
     *
     * @param u contenu initial
     */
    public static void creerFile(Flux u, String fileName) {
        try {
            PrintWriter sortie = new PrintWriter(new FileOutputStream(fileName));
            sortie.println(u.FluxToString());
            sortie.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static User FileToProfile(String fichier) {
        String s = "";
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
                s += ligne + "\n";
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        Flux f = new Flux(s);
        User u = new User();
        u = f.FluxToUser();
        return u;
    }

    public static void ajouterAmi(Friend f, String fileName){
        User u = new User();
        u = FileToProfile(fileName);
        File oldProfile = new File(fileName);
        oldProfile.delete();
        u.addFriend(f);
        Flux flux = new Flux(null, u, "USER");
        creerFile(flux, fileName);
    }
    public static void ajouterPost(Post p, String fileName){
        User u = new User();
        u = FileToProfile(fileName);
        File oldProfile = new File(fileName);
        oldProfile.delete();
        u.addPost(p);
        Flux flux = new Flux(null, u, "USER");
        creerFile(flux, fileName);
    }

}
