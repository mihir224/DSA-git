
class Car {
        private boolean engine;
        private int cylinders;
        private int wheels;
        private String name;

        public Car(int cylinders, String name) {
            this.engine = true;
            this.cylinders = cylinders;
            this.wheels = 4;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getCylinders() {
            return cylinders;
        }
        public String StartEngine()
        {
            return getClass().getSimpleName() + " -> startEngine()";
        }
        public String accelerate()
        {
            return getClass().getSimpleName() + " -> accelerate()";        }
        public String brake(){
            return getClass().getSimpleName() + " -> brake()";        }
    }
    class Mitsubishi extends Car{
        public Mitsubishi(int cylinders, String name) {
            super(cylinders, name);
        }

        @Override
        public String StartEngine() {
            return getClass().getSimpleName() + " -> startEngine()";
        }

        @Override
        public String accelerate() {
            return getClass().getSimpleName() + " -> accelerate()";
        }
        @Override
        public String brake() {
            return getClass().getSimpleName() + " -> brake()";
        }

    }

