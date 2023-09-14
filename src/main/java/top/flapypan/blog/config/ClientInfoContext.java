package top.flapypan.blog.config;

import lombok.Getter;

public class ClientInfoContext {

    @Getter
    public static class ClientInfo {
        private final String referrer;
        private final String ua;
        private final String ip;

        ClientInfo(String referrer, String ua, String ip) {
            this.referrer = referrer;
            this.ua = ua;
            this.ip = ip;
        }
    }

    private static final ThreadLocal<ClientInfo> clientInfoThreadLocal = new ThreadLocal<>();

    static void set(ClientInfo info) {
        clientInfoThreadLocal.set(info);
    }

    public static ClientInfo get() {
        return clientInfoThreadLocal.get();
    }

    static void remove() {
        clientInfoThreadLocal.remove();
    }
}
