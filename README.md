# Spring Boot Actuator Microservices Example

This project demonstrates how to implement monitoring and observability in Spring Boot microservices using **Spring Boot Actuator**, **Prometheus**, and **Grafana**.

It simulates a simple payment system with a gateway and a provider mock.

## 🏗 Architecture

The project consists of the following services orchestrated via Docker Compose:

| Service | Container Name | Port | Description |
|---------|----------------|------|-------------|
| **Payment Gateway** | `payment-gateway` | `8082` | Main application that processes payments. |
| **Payment Provider** | `provider-payment-mock` | `8081` | Mock service simulating an external payment provider. |
| **Prometheus** | `prometheus` | `9090` | Time-series database that scrapes metrics from the services. |
| **Grafana** | `grafana` | `3000` | Visualization dashboard for metrics. |

## 🚀 Getting Started

### Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Running the Services

1. Clone the repository (if you haven't already).
2. Build and start the services using Docker Compose:

```bash
docker-compose up -d --build
```

3. Verify that all containers are running:

```bash
docker-compose ps
```

## 📊 Monitoring & Endpoints

### 1. Spring Boot Actuator
Both the Gateway and the Mock Provider expose Actuator endpoints.

- **Payment Gateway Actuator**: [http://localhost:8082/actuator](http://localhost:8082/actuator)
- **Payment Provider Actuator**: [http://localhost:8081/actuator](http://localhost:8081/actuator)

**Key Endpoints:**
- `/actuator/health`: Health status of the application.
- `/actuator/info`: General application info.
- `/actuator/prometheus`: Metrics formatted for Prometheus scraping.
- `/actuator/metrics`: Raw metrics.

### 2. Prometheus
Access the Prometheus dashboard to query metrics.

- **URL**: [http://localhost:9090](http://localhost:9090)
- **Example Query**: `http_server_requests_seconds_count` or `jvm_memory_used_bytes`

Check the targets status to ensure both services are being scraped:
[http://localhost:9090/targets](http://localhost:9090/targets)

### 3. Grafana
Visualize the metrics with rich dashboards.

- **URL**: [http://localhost:3000](http://localhost:3000)
- **Credentials**:
  - User: `admin`
  - Password: `admin`

**Setup:**
1. Login to Grafana.
2. Go to **Configuration > Data Sources**.
3. Add a **Prometheus** data source.
   - URL: `http://prometheus:9090`
4. Create a new dashboard or import a standard Spring Boot dashboard (e.g., ID `11378`).

## 🛠 Configuration Details

### Prometheus Configuration (`prometheus.yml`)
The `prometheus.yml` file configures Prometheus to scrape metrics from the `payment-gateway` and `provider-payment-mock` services.

```yaml
scrape_configs:
  - job_name: 'payment-gateway'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8082', 'host.docker.internal:8081']
```

> **Note**: We use `host.docker.internal` to allow the Prometheus container to access services running on the host machine's ports (mapped via Docker).

## 📝 development

To modify the services, edit the source code in `actuator-example-spring/` or `payment-provider-mock/`, then run `docker-compose up -d --build` to apply changes.
