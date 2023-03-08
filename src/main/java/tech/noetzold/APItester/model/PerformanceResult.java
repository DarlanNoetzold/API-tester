package tech.noetzold.APItester.model;
public class PerformanceResult {
    private final long responseTime;
    private final int maxResponseSize;
    private final double requestsPerSecond;

    public PerformanceResult(long responseTime, int maxResponseSize, double requestsPerSecond) {
        this.responseTime = responseTime;
        this.maxResponseSize = maxResponseSize;
        this.requestsPerSecond = requestsPerSecond;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public int getMaxResponseSize() {
        return maxResponseSize;
    }

    public double getRequestsPerSecond() {
        return requestsPerSecond;
    }
}
