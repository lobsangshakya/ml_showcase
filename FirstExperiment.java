import java.util.*;

public class FirstExperiment {

    public static void main(String[] args) {
        try {
            // Simulation setup
            int numUsers = 1;
            System.out.println("Initializing simulation with " + numUsers + " user(s)...");
            simulateCloudEnvironment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void simulateCloudEnvironment() {
        // Step 1: Create Datacenter
        Datacenter datacenter0 = createDatacenter("Datacenter_0");

        // Step 2: Create Broker
        DatacenterBroker broker = new DatacenterBroker("Broker_0");
        int brokerId = broker.getId();

        // Step 3: Create VM
        Vm vm = new Vm(0, brokerId, 1000, 1, 512, 1000, 10000, "Xen");
        broker.submitVm(vm);

        // Step 4: Create Cloudlet (task)
        Cloudlet cloudlet = new Cloudlet(0, 40000, 1, 300, 300, brokerId, 0);
        broker.submitCloudlet(cloudlet);

        // Step 5: Simulate execution
        simulateExecution(cloudlet, vm, datacenter0);

        // Step 6: Print results
        List<Cloudlet> finished = new ArrayList<>();
        finished.add(cloudlet);
        printCloudletList(finished);
    }

    private static Datacenter createDatacenter(String name) {
        return new Datacenter(name, "x86", "Linux", "Xen", 10.0, 3.0);
    }

    private static void simulateExecution(Cloudlet c, Vm vm, Datacenter d) {
        c.setStatus("SUCCESS");
        c.setResourceId(d.getId());
        c.setVmId(vm.getId());
        c.setExecStartTime(0.1);
        c.setFinishTime(40.1);
        c.setActualCPUTime(40.0);
    }

    private static void printCloudletList(List<Cloudlet> list) {
        String indent = "    ";
        System.out.println("\n========== OUTPUT ==========");
        System.out.println("Cloudlet ID" + indent + "STATUS" + indent +
                "Data center ID" + indent + "VM ID" + indent + "Time" +
                indent + "Start Time" + indent + "Finish Time");

        for (Cloudlet cloudlet : list) {
            System.out.print(indent + cloudlet.getCloudletId() + indent + cloudlet.getStatus());
            System.out.println(indent + cloudlet.getResourceId() + indent + cloudlet.getVmId() +
                    indent + cloudlet.getActualCPUTime() + indent + cloudlet.getExecStartTime() +
                    indent + cloudlet.getFinishTime());
        }
    }
}

/* ------------------ Supporting Classes (Simulated) ------------------ */

class Datacenter {
    private static int counter = 0;
    private int id;
    private String name, arch, os, vmm;
    private double timeZone, cost;

    public Datacenter(String name, String arch, String os, String vmm, double timeZone, double cost) {
        this.id = counter++;
        this.name = name;
        this.arch = arch;
        this.os = os;
        this.vmm = vmm;
        this.timeZone = timeZone;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }
}

class DatacenterBroker {
    private static int counter = 0;
    private int id;
    private String name;
    private List<Vm> vmList = new ArrayList<>();
    private List<Cloudlet> cloudletList = new ArrayList<>();

    public DatacenterBroker(String name) {
        this.id = counter++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void submitVm(Vm vm) {
        vmList.add(vm);
    }

    public void submitCloudlet(Cloudlet cloudlet) {
        cloudletList.add(cloudlet);
    }
}

class Vm {
    private int id, brokerId, pesNumber, ram;
    private long bw, size;
    private String vmm;
    private double mips;

    public Vm(int id, int brokerId, double mips, int pesNumber, int ram, long bw, long size, String vmm) {
        this.id = id;
        this.brokerId = brokerId;
        this.mips = mips;
        this.pesNumber = pesNumber;
        this.ram = ram;
        this.bw = bw;
        this.size = size;
        this.vmm = vmm;
    }

    public int getId() {
        return id;
    }
}

class Cloudlet {
    private int id, pesNumber, userId, vmId, resourceId;
    private long length, fileSize, outputSize;
    private String status;
    private double execStartTime, finishTime, actualCPUTime;

    public Cloudlet(int id, long length, int pesNumber, long fileSize, long outputSize, int userId, int vmId) {
        this.id = id;
        this.length = length;
        this.pesNumber = pesNumber;
        this.fileSize = fileSize;
        this.outputSize = outputSize;
        this.userId = userId;
        this.vmId = vmId;
    }

    public int getCloudletId() {
        return id;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public int getVmId() {
        return vmId;
    }

    public void setResourceId(int id) {
        this.resourceId = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setExecStartTime(double execStartTime) {
        this.execStartTime = execStartTime;
    }

    public double getExecStartTime() {
        return execStartTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setActualCPUTime(double actualCPUTime) {
        this.actualCPUTime = actualCPUTime;
    }

    public double getActualCPUTime() {
        return actualCPUTime;
    }
}
