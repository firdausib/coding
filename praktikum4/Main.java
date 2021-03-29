class Motherboard {

    // static nested class
    static class USB{
        int usb2 = 2;
        int usb3 = 1;
        int getTotalPorts(){
            return usb2+usb3;
        }
    }
}
public class Main {
    public static void main(String[] args)
    {
        //create an object of the static nested class
        //using the name of the outer class
        Motherboard.USB usb = new Motherboard.USB();
        System.out.println("Total Ports = "+usb.getTotalPorts());
    }
}