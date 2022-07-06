package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final MapsClient mapsClient;
    private final PriceClient priceClient;
    private final CarRepository repository;

    public CarService(MapsClient mapsClient, PriceClient priceClient, CarRepository repository) {
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         * TODO: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         */
        Car carService ;
        Optional<Car> carOptional = repository.findById(id);
        if (carOptional.isPresent()) {
            carService = carOptional.get();
        } else {
            throw new CarNotFoundException();
        }
        try {
            String vehiclePrice = priceClient.getPrice(id);
            carService.setPrice(vehiclePrice);
        } catch (Exception e) {
            System.out.println("Vehicle not found with the ID %d " + id);
        }

        /**
         * TODO: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * TODO: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         */
        Location location = carOptional.get().getLocation();
        try {
            Location result = mapsClient.getAddress(location);
            carService.setLocation(result);
        } catch (Exception e) {
            System.out.println("Maps not found");
        }


        /**
         * TODO: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * TODO: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         */


        return carService;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param carService A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car carService) {
        if (carService.getId() != null) {
            return repository.findById(carService.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(carService.getDetails());
                        carToBeUpdated.setLocation(carService.getLocation());
                        carToBeUpdated.setCondition(carService.getCondition());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(carService);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * TODO: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else {
            throw new CarNotFoundException();
        }

        /**
         * TODO: Delete the car from the repository.
         */


    }
}
