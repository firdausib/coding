class CPU {
    double price;
    //nested class
    class Processor{

        //members of nested class
        double cores;
        String manufacturer;

        double getCache(){
            return 4.3;
        }
    }

    //nested protected class
    protected class RAM{

        //members of protected nested class
        double memory;
        String manufacturer;

        double getClockSpeed(){
            return 5.5;
        }
    }
}

public class Tugas {
    public static void main(String[] args){
        CPU cpu = new CPU();
        CPU.Processor processor = cpu.new Processor();
        CPU.RAM ram = cpu.new RAM();
        System.out.println("Processor Cache = " +processor.getCache());
        System.out.println("Ram Clock Speed = " +ram.getClockSpeed());
    }
}