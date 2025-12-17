# Smart Parking System 🚗

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5-green.svg)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Overview

The **Smart Parking System** is a comprehensive Java-based parking lot management system developed as part of the Design Patterns course at Ariel University. It demonstrates the practical implementation of **6 major design patterns** in a real-world scenario.

## ✨ Features

### Core Functionality
- 🚙 **Vehicle Management**: Add, remove, and manage cars and motorcycles
- ⏱️ **Parking Sessions**: Track parking sessions with automatic duration calculation
- 💳 **Dynamic Pricing**: Calculate fees based on vehicle type, duration, and disability status
- 🎫 **Subscription System**: Support for Standard and Premium subscription tiers
- 📊 **Reporting**: Generate detailed daily parking reports and statistics

### Technical Features
- 🖥️ **GUI Interface**: Modern graphical interface with Java Swing
- 💻 **CLI Interface**: Interactive command-line interface for easy operation
- ⚙️ **Configurable**: External configuration file for easy customization
- 📝 **Logging**: Built-in logging system for monitoring and debugging
- ⚠️ **Exception Handling**: Comprehensive custom exception hierarchy
- 🧪 **Fully Tested**: Extensive unit test coverage with JUnit 5

## 🏗️ Design Patterns Implemented

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **Singleton** | `ParkingLot`, `ParkingConfig` | Ensure single instance of core managers |
| **Factory Method** | `VehicleFactory`, `CarFactory`, `MotorcycleFactory` | Create vehicles without specifying concrete classes |
| **Builder** | `ParkingReportBuilder` | Construct complex report objects step by step |
| **Facade** | `ParkingSystemFacade` | Provide simplified interface to complex subsystems |
| **Observer** | `ParkingObserver`, `StatisticsObserver`, `DisplayObserver` | Notify components of parking events |
| **Prototype** | `VehicleCloner`, `Vehicle.clone()` | Clone existing vehicle objects |

## 📁 Project Structure

```
src/
├── parking/
│   ├── Main.java                    # Application entry point
│   ├── cli/                         # Command-line interface
│   │   └── ParkingCLI.java          # Interactive CLI application
│   ├── gui/                         # Graphical user interface
│   │   └── ParkingGUI.java          # Swing-based GUI application
│   ├── config/                      # Configuration management
│   │   └── ParkingConfig.java       # Centralized configuration
│   ├── core/                        # Core domain entities
│   │   ├── Vehicle.java             # Abstract vehicle class
│   │   ├── Car.java                 # Car implementation
│   │   ├── Motorcycle.java          # Motorcycle implementation
│   │   ├── ParkingSpot.java         # Parking spot entity
│   │   └── ParkingSession.java      # Parking session tracking
│   ├── exceptions/                  # Custom exceptions
│   │   ├── ParkingException.java    # Base exception
│   │   ├── VehicleNotFoundException.java
│   │   ├── ParkingFullException.java
│   │   ├── InvalidSubscriptionException.java
│   │   └── DuplicateVehicleException.java
│   ├── management/                  # Business logic
│   │   ├── ParkingLot.java          # Parking lot management (Singleton)
│   │   ├── PricingCalculator.java   # Fee calculation
│   │   ├── Subscription.java        # Subscription management
│   │   └── SubscriptionType.java    # Subscription types enum
│   ├── patterns/                    # Design pattern implementations
│   │   ├── builder/                 # Builder pattern
│   │   ├── facade/                  # Facade pattern
│   │   ├── factory/                 # Factory pattern
│   │   ├── observer/                # Observer pattern
│   │   └── prototype/               # Prototype pattern
│   ├── reports/                     # Reporting system
│   │   └── ParkingStatistics.java
│   └── util/                        # Utilities
│       └── ParkingLogger.java       # Custom logger
├── resources/
│   └── parking.properties           # Configuration file
test/
└── parking/test/                    # Unit tests (10 test classes)
```

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/smart-parking-system.git
cd smart-parking-system
```

2. Build with Maven:
```bash
mvn clean compile
```

3. Run tests:
```bash
mvn test
```

### Running the Application

#### Demo Mode (Main.java)
```bash
mvn exec:java -Dexec.mainClass="parking.Main"
```

#### Interactive CLI Mode
```bash
mvn exec:java -Dexec.mainClass="parking.cli.ParkingCLI"
```GUI Mode (Graphical Interface)
```bash
mvn exec:java -Dexec.mainClass="parking.gui.ParkingGUI"
```

#### Using compiled JAR
```bash
mvn package
java -jar target/smart-parking-system-1.0.0.jar
```

## 🖼️ Screenshots

### GUI Interface
The graphical interface provides:
- Real-time occupancy progress bar
- Vehicle table with all parked vehicles
- Activity log panel
- Easy-to-use action buttons
- Modern color scheme package
java -jar target/smart-parking-system-1.0.0.jar
```

## ⚙️ Configuration

The system can be configured via `src/resources/parking.properties`:

```properties
# Parking Lot Configuration
parking.spots.regular=100
parking.spots.disabled=20

# Pricing (per hour)
pricing.car.hourly=18.0
pricing.car.disabled.hourly=8.0
pricing.motorcycle.hourly=12.0
pricing.motorcycle.disabled.hourly=6.0

# Free parking duration (hours)
pricing.free.hours=2.0

# Subscription discounts
subscription.standard.discount=0.2
subscription.premium.discount=0.4
```

## 🧪 Testing

The project includes comprehensive unit tests with **10 test classes**:

| Test Class | Coverage |
|------------|----------|
| `ParkingLotTest` | Parking lot operations |
| `VehicleFactoryTest` | Factory pattern |
| `PricingCalculatorTest` | Fee calculations |
| `ParkingSessionTest` | Session management |
| `ParkingReportBuilderTest` | Builder pattern |
| `ObserverTest` | Observer pattern |
| `PrototypeTest` | Prototype pattern |
| `FacadeTest` | Facade pattern |
| `ConfigTest` | Configuration |
| `ExceptionTest` | Exception handling |

Run all tests:
```bash
mvn test
```

## 📊 Class Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    ParkingSystemFacade                       │
│                        (Facade)                              │
└─────────────────────────────────────────────────────────────┘
          │                    │                    │
          ▼                    ▼                    ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│   ParkingLot    │  │  VehicleFactory │  │ PricingCalculator│
│   (Singleton)   │  │    (Factory)    │  │                  │
└─────────────────┘  └─────────────────┘  └─────────────────┘
          │                    │
          ▼                    ▼
┌─────────────────┐  ┌─────────────────┐
│ ParkingObserver │  │    Vehicle      │
│   (Observer)    │  │   (Prototype)   │
└─────────────────┘  └─────────────────┘
```

## 👥 Authors

- **Kfir Moscovich** - *Developer*
- **Avi Mahari** - *Developer*

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Developed as part of the Design Patterns Course at Ariel University.

## 🙏 Acknowledgments

- Design Patterns course instructors at Ariel University
- Gang of Four for the original design patterns book
- JUnit team for the testing framework

---

⭐ **Star this repo if you found it helpful!**
