import java.sql.*;


public class Main {

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Start");
            Connection connect = DriverManager.getConnection("jdbc:mysql://dijkstra.ug.bcc.bilkent.edu.tr/burak_korkmaz","burak.korkmaz", "BqGpEqx1");
            System.out.println("Connected");


            Statement st = connect.createStatement();
            System.out.println("Statement Connected");

            st.execute("DROP TABLE IF EXISTS citizen_reconciliate,designate,has_statement,trial,pending_place,works,open,reconciliate,involves,has_lawyer,manage,gets_penalty, court,lawyer,conciliator,judge,lawsuit,citizen");
            System.out.println("Dropped Table");

            //2.1
            String citizen = "CREATE TABLE citizen (" +
                    "TCK_NO varchar(11) PRIMARY KEY," +
                    "name varchar(20) NOT NULL," +
                    "surname varchar(20) NOT NULL," +
                    "password varchar(100)," +
                    "phone_no varchar(11)," +
                    "email_address varchar(100)," +
                    "date_of_birth date," +
                    "age varchar (3)," +
                    "street_number varchar(100)," +
                    "street_name varchar(100)," +
                    "apt_number varchar(100)," +
                    "city varchar(30)," +
                    "state varchar(30)," +
                    "zip numeric(5,0));";

            //2.2
            String judge = "CREATE TABLE judge( " +
                    "TCK_NO varchar(11) NOT NULL, " +
                    "law_society_ID varchar(11) NOT NULL PRIMARY KEY, " +
                    "type varchar(30) , " +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen(TCK_NO))";

            //2.3
            String conciliator = "CREATE TABLE conciliator " +
                    "(TCK_NO VARCHAR(11) NOT NULL," +
                    "law_society_ID VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen(TCK_NO))";

            //2.4
            String lawyer = "CREATE TABLE lawyer " +
                    "(TCK_NO VARCHAR(11) NOT NULL," +
                    "law_society_ID VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "type VARCHAR(30) NOT NULL," +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen(TCK_NO))";

            //2.5
            String lawsuit = "CREATE TABLE lawsuit " +
                    "(lawsuit_ID int NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "category VARCHAR(20)," +
                    "public_access VARCHAR(20)," +
                    "status VARCHAR(20) DEFAULT 'waiting' ," +
                    "pending_place VARCHAR(100))";

            //2.6
            String court = "CREATE TABLE court " +
                    "(court_id int NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "name varchar (100) NOT NULL," +
                    "city varchar (20) NOT NULL," +
                    "type varchar (30));";

            //2.7
            String gets_penalty = "CREATE TABLE gets_penalty" +
                    "(lawsuit_ID int," +
                    "suspect_TCK_NO VARCHAR(11)," +
                    "penalty_type VARCHAR(10) NOT NULL," +
                    "year int," +
                    "fine int," +
                    "PRIMARY KEY(lawsuit_ID, suspect_TCK_NO)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID)," +
                    "FOREIGN KEY (suspect_TCK_NO) REFERENCES citizen(TCK_NO));";

            //2.8
            String manage = "CREATE TABLE manage(" +
                    "law_society_ID varchar(11)," +
                    "lawsuit_ID int ," +
                    "PRIMARY KEY (law_society_ID,lawsuit_ID)," +
                    "FOREIGN KEY (law_society_ID) REFERENCES judge(law_society_ID)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID) );";

            //2.9
            String has_lawyer = "CREATE TABLE has_lawyer " +
                    "(TCK_NO VARCHAR(11) NOT NULL," +
                    "law_society_ID VARCHAR(11) NOT NULL," +
                    "PRIMARY KEY (TCK_NO,law_society_ID)," +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen(TCK_NO)," +
                    "FOREIGN KEY (law_society_ID) REFERENCES lawyer(law_society_ID))";

            //2.10
            String reconciliate = "CREATE TABLE reconciliate " +
                    "(law_society_ID VARCHAR(11) NOT NULL," +
                    "lawsuit_ID int NOT NULL," +
                    "terms VARCHAR(100), " +
                    "decision VARCHAR(20) default 'processing' , " +
                    "PRIMARY KEY(law_society_ID, lawsuit_ID), " +
                    "FOREIGN KEY (law_society_ID) REFERENCES conciliator(law_society_ID)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID))";
            //2.11
            String involves = "CREATE TABLE involves (" +
                    "TCK_NO varchar(11) NOT NULL," +
                    "lawsuit_ID int NOT NULL," +
                    "role varchar (50), " +
                    "result varchar (10), " +
                    "penalty varchar (10), " +
                    "PRIMARY KEY (TCK_NO,lawsuit_ID)," +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen (TCK_NO)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit (lawsuit_ID));";
            //2.12
            String open = "CREATE TABLE open (" +
                    "law_society_ID varchar (11) NOT NULL," +
                    "lawsuit_ID int NOT NULL," +
                    "PRIMARY KEY (law_society_ID,lawsuit_ID)," +
                    "FOREIGN KEY (law_society_ID) REFERENCES lawyer(law_society_ID)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID));";

            //2.13
            String works = "CREATE TABLE works (" +
                    "court_id int NOT NULL," +
                    "law_society_ID varchar (11) NOT NULL," +
                    "PRIMARY KEY (law_society_ID)," +
                    "FOREIGN KEY (law_society_ID) REFERENCES judge (law_society_ID)," +
                    "FOREIGN KEY (court_id) REFERENCES court (court_id));";

            //2.14
            String pending_place = "CREATE TABLE pending_place (" +
                    "lawsuit_ID int NOT NULL," +
                    "court_id int NOT NULL," +
                    "date date ," +
                    "PRIMARY KEY (lawsuit_ID)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID)," +
                    "FOREIGN KEY (court_id) REFERENCES court(court_id));";
            //2.15
            String trial = "CREATE TABLE trial (" +
                    "lawsuit_ID int NOT NULL," +
                    "trial_date date," +
                    "PRIMARY KEY (lawsuit_ID,trial_date)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID));";


            //2.16
            String has_statement = "CREATE TABLE has_statement(" +
                    "TCK_NO varchar (11) NOT NULL," +
                    "lawsuit_ID int NOT NULL," +
                    "trial_date date, " +
                    "personal_statement varchar(50),"+
                    "PRIMARY KEY (lawsuit_ID,trial_date)," +
                    "FOREIGN KEY (TCK_NO) REFERENCES citizen(TCK_NO)," +
                    "FOREIGN KEY (lawsuit_ID,trial_date) REFERENCES trial(lawsuit_ID,trial_date));";
            //2.17
            String designate = "CREATE TABLE designate(" +
                    "judge_society_ID varchar (11) NOT NULL," +
                    "conciliator_society_ID varchar (11) NOT NULL," +
                    "lawsuit_ID int NOT NULL,"+
                    "PRIMARY KEY (lawsuit_ID)," +
                    "FOREIGN KEY (judge_society_ID) REFERENCES judge (law_society_ID)," +
                    "FOREIGN KEY (conciliator_society_ID) REFERENCES conciliator (law_society_ID)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit(lawsuit_ID));";
            //2.18
            String citizen_reconciliate = "CREATE TABLE citizen_reconciliate(" +
                    "citizen_TCK_NO varchar (11) NOT NULL," +
                    "lawsuit_ID int NOT NULL," +
                    "Citizen_decision varchar (5)," +
                    "PRIMARY KEY (lawsuit_ID,citizen_TCK_NO)," +
                    "FOREIGN KEY (citizen_TCK_NO) REFERENCES citizen (TCK_NO)," +
                    "FOREIGN KEY (lawsuit_ID) REFERENCES lawsuit (lawsuit_ID));";


            //2.1
            st.execute(citizen);
            System.out.println("citizen Created");

            //2.2
            st.execute(judge);
            System.out.println("judge Created");

            //2.3
            st.execute(conciliator);
            System.out.println("conciliator Created");

            //2.4
            st.execute(lawyer);
            System.out.println("lawyer Created");

            //2.5
            st.execute(lawsuit);
            System.out.println("lawsuit Created");

            //2.6
            st.execute(court);
            System.out.println("court Created");

            //2.7
            st.execute(gets_penalty);
            System.out.println("gets_penalty Created");

            //2.8
            st.execute(manage);
            System.out.println("manage Created");

            //2.9
            st.execute(has_lawyer);
            System.out.println("has_lawyer Created");

            //2.10
            st.execute(reconciliate);
            System.out.println("reconciliate Created");

            //2.11
            st.execute(involves);
            System.out.println("involves Created");

            //2.12
            st.execute(open);
            System.out.println("open Created");

            //2.13
            st.execute(works);
            System.out.println("works Created");

            //2.14
            st.execute(pending_place);
            System.out.println("pending_place Created");

            //2.15
            st.execute(trial);
            System.out.println("trial Created");

            //2.16
            st.execute(has_statement);
            System.out.println("has_statement Created");

            //2.17
            st.execute(designate);
            System.out.println("designate Created");

            //2.18
            st.execute(citizen_reconciliate);
            System.out.println("citizen_reconciliate Created");

            //2.1
            st.execute("INSERT INTO citizen VALUES " +
                    "('10312178564' , 'Ege' , 'Akin' , '12345', '05069252527', 'ege.akin@ug.bilkent.edu.tr', '2011-01-01', '21', '31', 'Cukurambar', '69', 'Ankara', 'Turkiye', 60000  )," +
                    "('36145544574' , 'Burak' , 'Korkmaz' , '12345', '05394240707', 'burak.korkmaz@ug.bilkent.edu.tr', '2011-01-01', '21', '31', 'Cukurambar', '69', 'Ankara', 'Turkiye', 60000  )," +
                    "('10312178565' , 'Firat' , 'Kalpkiran' , '12345', '05069252527', 'firat.kalpkiran@ug.bilkent.edu.tr', '2011-01-01', '21', '31', 'Cukurambar', '69', 'Ankara', 'Turkiye', 60000 )," +
                    "('36145544575' , 'Kurak' , 'Borkmaz' , '12345', '05394240707', 'kurak.borkmaz@ug.bilkent.edu.tr', '2011-01-01', '21', '31', 'Cukurambar', '69', 'Ankara', 'Turkiye', 60000  );" );

            System.out.println("citizen Inserted");


            //2.2
            st.execute("INSERT INTO judge VALUES " +
                            "('36145544574' , 'J1' , 'Yargi' );" );

            System.out.println("judge Inserted");

            //2.3
            st.execute("INSERT INTO conciliator VALUES " +
                    "('10312178565' , 'C1');");

            System.out.println("conciliator Inserted");

            //2.4
            st.execute("INSERT INTO lawyer VALUES " +
                    "('36145544575' , 'L1','savunmaci');");

            System.out.println("lawyer Inserted");

            //2.5
            st.execute("ALTER TABLE lawsuit AUTO_INCREMENT = 1;");

            System.out.println("Alter");

            st.execute("INSERT INTO lawsuit (category, public_access ,pending_place) VALUES" +
                    "('aile davasi','public_accesible' , 'Ankara Adalet Sarayi')," +
                    "('hirsizlik','public_accesible' , 'Istanbul'); ");

            System.out.println("lawsuit Inserted");

            //2.6
            st.execute("ALTER TABLE court AUTO_INCREMENT = 1;");

            System.out.println("Alter");

            st.execute("INSERT INTO court (name,city,type) VALUES " +
                    "('Ankara Adalet Sarayi' , 'Ankara','ceza')," +
                    "('Caglayan Adalet Sarayi' , 'Istanbul','ceza');");


            System.out.println("court Inserted");

            //2.7
            st.execute("INSERT INTO gets_penalty VALUES " +
                    "(1,'10312178564','CINAYET' , 1998 , 20000);");

            System.out.println("gets_penalty Inserted");

            //2.8
            st.execute("INSERT INTO manage VALUES " +
                    "('J1' , '1');");

            System.out.println("manage Inserted");

            //2.9
            st.execute("INSERT INTO has_lawyer VALUES " +
                    "(10312178564,'L1');");

            System.out.println("has_lawyer Inserted");

            //2.10
            st.execute("INSERT INTO reconciliate VALUES " +
                    "('C1', '2', 'term1', 'abi barisin artik' ),('C1', '1', 'yemek yesek mi', 'abi barisin artik') ; ");
            System.out.println("reconciliate Inserted");

            //2.11
            st.execute("INSERT INTO involves VALUES " +
                    "(36145544574,'1','victim','','')," +
                    "(36145544575,'2','victim','','');");

            System.out.println("involves Inserted");

            //2.12
            st.execute("INSERT INTO open VALUES " +
                    "('L1','1');");

            System.out.println("open Inserted");

            //2.13
            st.execute("INSERT INTO works VALUES " +
                    "('1','J1');");

            System.out.println("works Inserted");

            //2.14
            st.execute("INSERT INTO pending_place VALUES " +
                    "('1','1','1998-10-15');");

            System.out.println("pending_place Inserted");

            //2.15
            st.execute("INSERT INTO trial VALUES " +
                    "('1','1998-10-20')," +
                    "('1','2010-10-20');");

            System.out.println("pending_place Inserted");

            //2.16
            st.execute("INSERT INTO has_statement VALUES " +
                    "('36145544574','1','1998-10-20','');");

            System.out.println("has_statement Inserted");

            //2.17
            st.execute("INSERT INTO designate VALUES " +
                    "('J1','C1','1');");

            System.out.println("designate Inserted");
            //2.18
            st.execute("INSERT INTO citizen_reconciliate VALUES " +
                    "('36145544574','1','yes');");

            System.out.println("citizen_reconciliate Inserted");


            System.out.println("Contents of each table is printing...");
            System.out.println();


            ResultSet resultSet1 = st.executeQuery("SELECT * FROM citizen");

            System.out.println("1");
            System.out.println("TCK_NO \t\t\t  name \t surname \t password \t phone_no \t\t email_address \t\t\t\t\t "  +
                    "date_of_birth \t age \t street_number \t street_name \t apt_number \t city " +
                    " \t\t state \t\t zip");

            while(resultSet1.next() ) {
                System.out.println(
                        resultSet1.getString("TCK_NO") + " \t " +
                        resultSet1.getString("name") + " \t " +
                        resultSet1.getString("surname") + " \t\t " +
                        resultSet1.getString("password") + "  \t " +
                        resultSet1.getString("phone_no") + " \t " +
                        resultSet1.getString("email_address") + " \t " +
                        resultSet1.getString("date_of_birth") + "  \t " +
                        resultSet1.getString("age") + " \t " +
                        resultSet1.getString("street_number") + " \t\t\t " +
                        resultSet1.getString("street_name") + "  \t " +
                        resultSet1.getString("apt_number") + " \t\t\t " +
                        resultSet1.getString("city") + "  \t " +
                        resultSet1.getString("state") + " \t " +
                        resultSet1.getString("zip") );
            }

            System.out.println("2");


            ResultSet resultSet2 = st.executeQuery("SELECT * FROM judge");

            System.out.println("TCK_NO \t\t\t law_society_ID \t type");

            while(resultSet2.next() ) {
                System.out.println(
                        resultSet2.getString("TCK_NO") + " \t " +
                        resultSet2.getString("law_society_ID") + " \t " +
                        resultSet2.getString("type") + " \t ");
            }

            System.out.println("3");

            ResultSet resultSet3 = st.executeQuery("SELECT * FROM conciliator");

            System.out.println("TCK_NO \t\t\t law_society_ID");

            while(resultSet3.next() ) {
                System.out.println(
                        resultSet3.getString("TCK_NO") + " \t " +
                        resultSet3.getString("law_society_ID"));
            }

            System.out.println("4");


            ResultSet resultSet4 = st.executeQuery("SELECT * FROM lawyer");

            System.out.println("TCK_NO \t\t\t law_society_ID \t type");

            while(resultSet4.next() ) {
                System.out.println(
                        resultSet4.getString("TCK_NO") + " \t " +
                        resultSet4.getString("law_society_ID") + " \t " +
                        resultSet4.getString("type") + " \t ");
            }

            System.out.println("5");

            ResultSet resultSet5 = st.executeQuery("SELECT * FROM lawsuit");

            System.out.println("lawsuit_ID \t\t\t category \t public_access \t status \t pending_place");

            while(resultSet5.next() ) {
                System.out.println(
                        resultSet5.getString("lawsuit_ID") + " \t " +
                        resultSet5.getString("category") + " \t " +
                        resultSet5.getString("public_access") + " \t "+
                        resultSet5.getString("status") + " \t "+
                        resultSet5.getString("pending_place") );

            }
            System.out.println("6");

            ResultSet resultSet6 = st.executeQuery("SELECT * FROM court");

            System.out.println("name \t\t\t city \t type ");

            while(resultSet6.next() ) {
                System.out.println(
                        resultSet6.getString("name") + " \t " +
                        resultSet6.getString("city") + " \t " +
                        resultSet6.getString("type") );

            }
            System.out.println("7");

            ResultSet resultSet7 = st.executeQuery("SELECT * FROM gets_penalty");
            System.out.println("lawsuit_ID \t\t\t suspect_TCK_NO \t penalty_type \t year \t fine");

            while(resultSet7.next() ) {
                System.out.println(
                        resultSet7.getString("lawsuit_ID") + " \t " +
                                resultSet7.getString("suspect_TCK_NO") + " \t " +
                                resultSet7.getString("penalty_type") + " \t "+
                                resultSet7.getString("year") + " \t "+
                                resultSet7.getString("fine") );

            }
            System.out.println("8");

            ResultSet resultSet8 = st.executeQuery("SELECT * FROM manage");

            System.out.println("law_society_ID \t\t\t lawsuit_ID ");

            while(resultSet8.next() ) {
                System.out.println(
                        resultSet8.getString("law_society_ID") + " \t " +
                        resultSet8.getString("lawsuit_ID") );

            }
            System.out.println("9");

            ResultSet resultSet9 = st.executeQuery("SELECT * FROM has_lawyer");

            System.out.println("TCK_NO \t\t\t law_society_ID ");

            while(resultSet9.next() ) {
                System.out.println(
                        resultSet9.getString("TCK_NO") + " \t " +
                        resultSet9.getString("law_society_ID") );

            }
            System.out.println("10");

            ResultSet resultSet10 = st.executeQuery("SELECT * FROM reconciliate");

            System.out.println("law_society_ID \t\t\t lawsuit_ID  \t decision");

            while(resultSet10.next() ) {
                System.out.println(
                        resultSet10.getString("law_society_ID") + " \t " +
                                resultSet10.getString("lawsuit_ID") + " \t " +
                                resultSet10.getString("decision"));
            }
            System.out.println("11");

            ResultSet resultSet11 = st.executeQuery("SELECT * FROM involves");

            System.out.println("TCK_NO \t\t\t lawsuit_ID  \t role");

            while(resultSet11.next() ) {
                System.out.println(
                    resultSet11.getString("TCK_NO") + " \t " +
                    resultSet11.getString("lawsuit_ID") + " \t " +
                    resultSet11.getString("role"));
            }

            System.out.println("12");

            ResultSet resultSet12 = st.executeQuery("SELECT * FROM open");

            System.out.println("law_society_ID \t\t\t lawsuit_ID  ");

            while(resultSet12.next() ) {
                System.out.println(
                        resultSet12.getString("law_society_ID") + " \t " +
                        resultSet12.getString("lawsuit_ID"));
            }

            System.out.println("13");

            ResultSet resultSet13 = st.executeQuery("SELECT * FROM works");

            System.out.println("court_id  \t law_society_ID  ");

            while(resultSet13.next() ) {
                System.out.println(
                        resultSet13.getString("court_id") + " \t " +
                                resultSet13.getString("law_society_ID"));
            }

            System.out.println("14");

            ResultSet resultSet14 = st.executeQuery("SELECT * FROM pending_place");

            System.out.println("lawsuit_ID \t  court_id \t law_society_ID  ");

            while(resultSet14.next() ) {
                System.out.println(
                        resultSet14.getString("lawsuit_ID") + " \t " +
                                resultSet14.getString("court_id") + " \t " +
                                resultSet14.getString("date"));
            }


            System.out.println("15");

            ResultSet resultSet15 = st.executeQuery("SELECT * FROM trial");

            System.out.println("lawsuit_ID  \t trial_date  ");

            while(resultSet15.next() ) {
                System.out.println(
                        resultSet15.getString("lawsuit_ID") + " \t " +
                        resultSet15.getString("trial_date"));
            }


            System.out.println("16");

            ResultSet resultSet16 = st.executeQuery("SELECT * FROM has_statement");

            System.out.println("TCK_NO \t lawsuit_ID  \t trial_date \t personal_statement ");

            while(resultSet16.next() ) {
                System.out.println(
                        resultSet16.getString("TCK_NO") + " \t " +
                                resultSet16.getString("lawsuit_ID") + " \t " +
                                resultSet16.getString("trial_date") + " \t " +
                                resultSet16.getString("personal_statement"));
            }

            System.out.println("17");

            ResultSet resultSet17 = st.executeQuery("SELECT * FROM designate");

            System.out.println("judge_society_ID  \t conciliator_society_ID \t lawsuit_ID ");

            while(resultSet17.next() ) {
                System.out.println(
                        resultSet17.getString("judge_society_ID") + " \t " +
                                resultSet17.getString("conciliator_society_ID") + " \t " +
                        resultSet17.getString("lawsuit_ID"));
            }


            System.out.println("18");

            ResultSet resultSet18 = st.executeQuery("SELECT * FROM citizen_reconciliate");

            System.out.println("citizen_TCK_NO  \t lawsuit_ID \t Citizen_decision ");

            while(resultSet18.next() ) {
                System.out.println(
                        resultSet18.getString("citizen_TCK_NO") + " \t " +
                                resultSet18.getString("lawsuit_ID") + " \t " +
                                resultSet18.getString("Citizen_decision"));
            }
        }


        catch (Exception e) {
            System.out.println(e);
        }
    }
}
