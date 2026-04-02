import java.util.*;

// Represents an Add-On Service
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manages Add-On Services for Reservations
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    // Add services to a reservation
    public void addService(String reservationId, Service service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of add-on services
    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = reservationServicesMap.get(reservationId);

        if (services == null) return 0.0;

        double total = 0.0;
        for (Service service : services) {
            total += service.getCost();
        }
        return total;
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation ID " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s);
        }
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();

        int choice;

        do {
            System.out.println("\nSelect Add-On Service:");
            System.out.println("1. Breakfast (₹200)");
            System.out.println("2. Airport Pickup (₹500)");
            System.out.println("3. Extra Bed (₹300)");
            System.out.println("4. Finish Selection");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, new Service("Breakfast", 200));
                    break;
                case 2:
                    manager.addService(reservationId, new Service("Airport Pickup", 500));
                    break;
                case 3:
                    manager.addService(reservationId, new Service("Extra Bed", 300));
                    break;
                case 4:
                    System.out.println("Finalizing selection...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        // Display selected services
        System.out.println("\n--- Summary ---");
        manager.displayServices(reservationId);

        // Show total cost
        double totalCost = manager.calculateTotalServiceCost(reservationId);
        System.out.println("Total Add-On Cost: ₹" + totalCost);

        scanner.close();
    }
}