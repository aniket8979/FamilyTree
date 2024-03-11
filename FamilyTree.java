package MyTools;


import java.util.*;




class Person {

    private String name;
    private HashMap<String, List<Person>> relations = new HashMap<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRelation(String relation) {
        if (!relations.containsKey(relation)) {
            relations.put(relation, new ArrayList<>());
        }
    }

    // This method handles the logic of adding realtions to person.
    public void addRelation(String relation, Person person) {
        if (!relations.containsKey(relation)) {
            relations.put(relation, new ArrayList<>());
        }
        relations.get(relation).add(person);
    }

    public List<Person> getRelation(String relation) {
        return relations.get(relation);
    }

}





// This is the main driver class
public class FamilyTree {


    // This varibale is used to hold information of all members of a family tree.
    static HashMap<String, Person> people = new HashMap<>();


    static void addPerson(String name) {
        if (!people.containsKey(name)) {
            people.put(name, new Person(name));
        }
    }


    // This method initialise a new relationship for each member of family tree
    static void newRelation(String relation) {
        for (Map.Entry<String, Person> person : people.entrySet()) {
            person.getValue().addRelation(relation);
        }
    }


    // This method establish connection between two related persons, it also check for errors.
    static void addConnection(String name1, String relation, String name2) {
        Person person1 = people.get(name1);
        Person person2 = people.get(name2);
        if (person1 == null){
            System.out.println("family member " + name1 + " do not exists");   
        }
        if (person2 == null) {
            System.out.println("family member " + name2 + " do not exists");
        }
        else {
            person2.addRelation(relation, person1);
        }
    }


    // this method count number of relations, also handles error conditions
    static void countRelations(String relation, String name) {
        Person person = people.get(name);
        if (person == null) {
            System.out.println("Person do not exists");
            ;
        } else {
            try {
                // Here Wives-wife seems an edge, so a temperory solutio is used.
                if (relation.equals("wives")) {
                    relation = "wifes";
                }
                String relationship = relation.substring(0, relation.length() - 1);
                List<Person> allRelated = person.getRelation(relationship);
                System.out.println(allRelated.size());
            } catch (Exception e) {
                System.out.println(0);
            }
        }

    }
    

    static void getFather(String name) {
        Person person = people.get(name);
        List<Person> allRelated = person.getRelation("father");

        System.out.println(allRelated.get(0).getName());
    }

    // this method is provided to print names of all the family members in the family tree
    static void showFamilyMembers() {
        for (Map.Entry<String, Person> person : people.entrySet()) {
            System.out.println(person.getValue().getName());
        }

    }


    
    // this is the Main method.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("exit")) {
            System.out.println("");
            System.out.println("Enter command:");
            command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[1]) {
                case "add":
                    if (parts[1].equals("add") && (!parts[2].equals("relationship"))) {
                        addPerson(parts[2] + " " + parts[3]);
                    } else if (parts[2].equals("relationship")) {
                        newRelation(parts[3]);
                    }
                    break;

                case "connect":
                    addConnection(parts[2] + " " + parts[3], parts[5], parts[7] + " " + parts[8]);
                    break;

                case "count":
                    countRelations(parts[2], parts[4]+" "+parts[5]);
                    break;
                case "father":
                    getFather(parts[3]+" "+parts[4]);
                    break;
                case "print":
                    showFamilyMembers();
                case "exit":
                    command = "exit";
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
        scanner.close();
    }


}
