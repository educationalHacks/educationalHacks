
/* Name: Shiv Patel 
Assignment 4 */
public class Foothill
{
   public static void main(String[] args)
   {
      //Adding writers.
      Writer writer1 = new Writer("Stan Lee", 120000, 15.0, 
            true, true, true, "Executive Producer");  

      Writer writer2 = new Writer("Bob Kane", 100000, 13.0, 
            true, true, true, "Producer");

      Writer writer3 = new Writer(); //Default writer. 

      //Adding actors.
      Actor actor1 = new Actor("Keyser Soze", 80000, 9.0, 'M', 35);
      Actor actor2 = new Actor();//Default actor. 

      //Printing few writer and actors data.
      System.out.println("--- Displaying a few of Writer's and Actor's" 
            + " data to the console ---");
      System.out.println("Writer's info:");
      System.out.println("Writer has technical background: " 
            + writer1.getTechnicalBg());
      System.out.println("Writer has government background: " 
            + writer1.getGovernmentBg());
      System.out.println("Writer has international travel experience: " 
            + writer1.getInternationalXp());
      System.out.println("Writer rank: " + writer1.getRank() + "\n");

      System.out.println("Actor's info:");
      System.out.println("Actor's gender: " + actor1.getGender());
      System.out.println("Actor's age: " + actor1.getAge());

      //adding clients to agent's list.
      Agent agent1 = new Agent("Shiv Patel");
      agent1.removeClient(writer2);
      agent1.addClient(writer1);
      agent1.addClient(writer3);
      agent1.addClient(actor1);
      agent1.addClient(actor2);

      //Printing Agent's Clients' names.
      System.out.println();
      System.out.println("--- List of agent's client's name ---");
      System.out.println(agent1.toStringClientsShort());
      //printing list of all the Clients. 
      System.out.println();
      System.out.println("--- List of agent's all client information. ---");
      System.out.println(agent1.toStringClientsLong());

      //Removing writer2 which was not added to the agent's list.
      System.out.println();
      System.out.println("--- Removing client from agent's list. ---");
      if(!agent1.removeClient(writer2))
      {
         System.out.println("The client '" + writer2.getName() 
               + "' was not found on the agent's list.");
      }  
      //Removing writer3 from the agent's list.
      if(agent1.removeClient(writer3))
      {
         System.out.println("The client '" + writer3.getName() 
               + "' was removed from the agent's list.");
      }
      //Printing the list after a client is removed.
      System.out.println();
      System.out.println("--- Agent's client's list after"
            + " removing a client. ---");
      System.out.println(agent1.toStringClientsLong());

      //Displaying agent's name and income.
      System.out.println();
      System.out.println("--- Displaying agent information. ---");
      System.out.println("Agent: " + agent1.getAgentName() 
            + "\nThis years income: $" + agent1.getIncome());

   }
}


class Client
{
   private String name;
   private long incomeThisYear;
   private double percentCut;

   private static final int MIN_NAME_LENGTH = 3;
   private static final long MIN_INCOME = 0;
   private static final double MIN_PERCENTAGE_CUT = 0;

   private static final int MAX_NAME_LENGTH = 60;
   private static final long MAX_INCOME = 1000000000;
   private static final double MAX_PERCENTAGE_CUT = 99.99;

   public Client()
   {
      this("Default Client Bob", 45000, 10.0);//Default values.
   }

   public Client(String name, long income, double percent)
   {
      setName(name);
      setIncomeThisYear(income);
      setPercentCut(percent);
   }

   public boolean setName(String clientName)
   {
      if(isLegalChar(clientName))
      {
         this.name = clientName;
         return true;
      }else{
         System.out.println("Enter client name between 3 and 60 characters.");
         return false;
      }
   }

   public boolean setIncomeThisYear(long income)
   {
      if(isLegalIncome(income))
      {
         this.incomeThisYear = income;
         return true;
      }else{
         System.out.println("Please enter values between 0 and $1 billion.");
         return false;
      }
   }

   public boolean setPercentCut(double percentCut)
   {
      if(isLegalPercentCut(percentCut))
      {
         this.percentCut = percentCut;
         return true;
      }else{
         percentCut = 10.0;//"default 10"
         return false;
      }
   }

   public String getName()
   {
      return name;
   }

   public long getIncomeThisYear()
   {
      return incomeThisYear;
   }

   public double getPercentCut()
   {
      return percentCut;
   }

   private static boolean isLegalChar(String checkName)
   {
      if(checkName.length() >= MIN_NAME_LENGTH 
            && checkName.length() <= MAX_NAME_LENGTH)
         return true;
      else
         return false;
   }   

   private static boolean isLegalIncome(long checkIncome)
   {
      //incomeThisYear legal values between 0 and $1 billion.
      if(checkIncome >= MIN_INCOME && checkIncome <= MAX_INCOME)
         return true;
      else
         return false;
   }   

   private static boolean isLegalPercentCut(double checkPercentCut)
   {
      //percentCut legal values between 0 and 99.99
      if(checkPercentCut >= MIN_PERCENTAGE_CUT 
            && checkPercentCut <= MAX_PERCENTAGE_CUT 
            && !Double.isNaN(checkPercentCut))
         return true;
      else
         return false;
   }

   public String toString()
   {
      String result; 
      result = "Client name: " + name + "\n"
            + "Income this year: " + incomeThisYear + "\n"
            + "Percent cut: " + percentCut + "\n";
      return result;
   }

}


class Writer extends Client
{
   private boolean technical;
   private boolean government;
   private boolean international;
   private String rank;

   public Writer()
   {
      this("Default Writer John", 60000, 10.0, 
            true, true, true, "staff writer");
   }

   public Writer(String name, long income, double percentage, 
         boolean tech, boolean gov, boolean inter, String rankLevel)
   {
      super(name, income, percentage);
      setTechnicalBg(tech);
      setGovernmentBg(gov);
      setInternationalXp(inter);
      setRank(rankLevel);
   }

   //4 mutators
   //set technical background 
   public void setTechnicalBg(boolean technical)
   {  
      //boolean member so no validation required.
      this.technical = technical;
   }

   //set government background
   public void setGovernmentBg(boolean government)
   {  
      //boolean member so no validation require
      this.government = government;
   }

   //set international experience
   public void setInternationalXp(boolean international)
   {
      //boolean member so no validation require
      this.international = international;
   }

   //set rank of level
   public boolean setRank(String rank)
   {
      //validating rank
      if(isLegalRank(rank))
      {
         this.rank = rank;
         return true;
      }
      return false;
   }

   //4 accessors
   public boolean getTechnicalBg()
   {
      return technical;
   }

   public boolean getGovernmentBg()
   {
      return government;
   }

   public boolean getInternationalXp()
   {
      return international;
   }

   public String getRank()
   {
      return rank;
   }

   //validating rank
   private static boolean isLegalRank(String checkRank)
   {
      String[] commonRanks = {"Staff Writer", "Story Editor", "Co-Producer", 
            "Producer", "Co-Executive Producer", "Executive Producer"};
      for(int k=0; k < commonRanks.length; k++)
      {
         if(checkRank.equalsIgnoreCase(commonRanks[k]))
            return true;
      }
      return false;
   }

   //overriding toString() 
   public String toString()
   {
      String result; 
      result = "Writer has technical background: " + technical + "\n"
            + "Writer has government background: " + government + "\n"
            + "Writer has international travel experience: " + international
            + "\n"
            + "Writer rank: " + rank + "\n";
      result = super.toString() + result;
      return result;
   }

}


class Actor extends Client
{
   private char gender;
   private int age;

   public Actor()
   {
      this("Default Actor Alice", 50000, 10.0, 'M', 28);
   }

   public Actor(String name, long income, double percentage, 
         char gender, int age)
   {
      super(name, income, percentage);
      setGender(gender);
      setAge(age);
   }

   //2 mutators
   public boolean setGender(char gender)
   {
      //validating gender to be either M or F.
      //Converting checkGender to uppercase to simplify.
      gender = Character.toUpperCase(gender);
      if(isLegalGender(gender))
      {
         this.gender = gender;
         return true;
      }else {
         return false;
      }
   }

   public boolean setAge(int age)
   {
      //validating age to be between 0 to 120.
      if(isLegalAge(age))
      {
         this.age = age;
         return true;
      }else{
         return false;
      }
   }

   //2 accessors
   public char getGender()
   {
      return gender;
   }

   public int getAge()
   {
      return age;
   }

   public boolean isLegalGender(char checkGender)
   {
      if(checkGender == 'M' || checkGender == 'F')
         return true;
      else
         return false;
   }

   public boolean isLegalAge(int checkAge)
   {
      if(checkAge >= 0 && checkAge <= 120)
         return true;
      else
         return false;
   }

   //overriding toString()
   public String toString()
   {
      String result; 
      result = "Actor's gender: " + gender  + "\n"
            + "Actor's age: " + age  + "\n"; 
      result = super.toString() + result;
      return result;
   }

}


class Agent
{
   private String name; 
   private Client[] myClients;
   private int numClients;

   private static final int MAX_CLIENTS = 10;
   private static final int MIN_NAME_LEN = 3;
   private static final int MAX_NAME_LEN = 60;

   public Agent(String agentName)
   {
      myClients = new Client[MAX_CLIENTS]; 
      setAgentName(agentName);
   }

   public boolean setAgentName(String agentName)
   {
      if(isLetters(agentName))
      {
         this.name = agentName;
         return true;
      }else{
         System.out.println("Enter client name between 3 and 60 characters.");
         return false;
      }
   }

   public String getAgentName()
   {
      return name;
   }

   public int getNumClients()
   {
      return numClients;
   }

   private static boolean isLetters(String checkAgentName)
   {
      if(checkAgentName.length() >= MIN_NAME_LEN 
            && checkAgentName.length() <= MAX_NAME_LEN)
         return true;
      else
         return false;
   } 

   //puts client into the Agent's list.
   public boolean addClient(Client client)
   {
      if(numClients < MAX_CLIENTS)
      {
         myClients[numClients++] = client;
         return true;
      }else{
         return false;
      }
   }

   public boolean removeClient(Client client)
   {
      boolean move = false;

      for(int k=0; k < numClients; k++)
      {  
         if(move)
         {  //if match found, shift all the other clients to the left by one.
            myClients[k - 1] = myClients[k];
         }
         //Check if the client matches any client in the myClients array.
         if(myClients[k] == client)
         {
            move = true;
         }
      }
      if(move)
      {
         myClients[numClients - 1] = null;
         numClients-- ;
      }
      return move;
   }

   public String toStringClientsShort() 
   {
      //returns a list of all this Agent's Clients' names (only names).
      String returnSrt = myClients[0].getName();
      for(int k =1; k < numClients; k++)
      {
         Client temp = myClients[k];
         returnSrt = returnSrt + "\n" + temp.getName();
      }
      return returnSrt;
   }

   public String toStringClientsLong()
   {
      //returns a list of all the Clients, using the Client display method.
      String returnSrt = myClients[0].toString();
      for(int k =1; k < numClients; k++)
      {
         Client temp = myClients[k];
         returnSrt = returnSrt + "\n" + temp.toString();
      }
      return returnSrt;
   }

   public double getIncome()
   {
      double agentIncome = 0;
      for(int k = 0; k < numClients; k++)
      {
         agentIncome += ((myClients[k].getPercentCut()/100) 
               * myClients[k].getIncomeThisYear());
      }
      return agentIncome;
   }
}

/*------------------- paste of run from Console window -----------------------
--- Displaying a few of Writer's and Actor's data to the console ---
Writer's info:
Writer has technical background: true
Writer has government background: true
Writer has international travel experience: true
Writer rank: Executive Producer

Actor's info:
Actor's gender: M
Actor's age: 35

--- List of agent's client's name ---
Stan Lee
Default Writer John
Keyser Soze
Default Actor Alice

--- List of agent's all client information. ---
Client name: Stan Lee
Income this year: 120000
Percent cut: 15.0
Writer has technical background: true
Writer has government background: true
Writer has international travel experience: true
Writer rank: Executive Producer

Client name: Default Writer John
Income this year: 60000
Percent cut: 10.0
Writer has technical background: true
Writer has government background: true
Writer has international travel experience: true
Writer rank: staff writer

Client name: Keyser Soze
Income this year: 80000
Percent cut: 9.0
Actor's gender: M
Actor's age: 35

Client name: Default Actor Alice
Income this year: 50000
Percent cut: 10.0
Actor's gender: M
Actor's age: 28


--- Removing client from agent's list. ---
The client 'Bob Kane' was not found on the agent's list.
The client 'Default Writer John' was removed from the agent's list.

--- Agent's client's list after removing a client. ---
Client name: Stan Lee
Income this year: 120000
Percent cut: 15.0
Writer has technical background: true
Writer has government background: true
Writer has international travel experience: true
Writer rank: Executive Producer

Client name: Keyser Soze
Income this year: 80000
Percent cut: 9.0
Actor's gender: M
Actor's age: 35

Client name: Default Actor Alice
Income this year: 50000
Percent cut: 10.0
Actor's gender: M
Actor's age: 28


--- Displaying agent information. ---
Agent: Shiv Patel
This years income: $30200.0

--------------------------------------------------------------------- */



