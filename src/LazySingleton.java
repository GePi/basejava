public final class LazySingleton {
    private static volatile LazySingleton INSTANCE;
    private final static Object LOCK = new Object();

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private final static LazySingleton INSTANCE;

        static {
            INSTANCE = new LazySingleton();
        }
    }

    public LazySingleton getInstance(boolean onDemandHolderIdiom) {
        return LazySingletonHolder.INSTANCE;
    }

    public LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}