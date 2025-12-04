# Stripe Integration API

A Spring Boot application for integrating with Stripe's payment processing services. This API provides endpoints for handling payments, invoices, subscriptions, refunds, and webhooks.

## Features

- **Payment Intents**: Create, confirm, and cancel payment intents.
- **Invoices**: Create and retrieve invoices.
- **Subscriptions**: Manage recurring billing and subscriptions.
- **Checkout Sessions**: Create hosted checkout sessions.
- **Refunds**: Process refunds for payments.
- **Webhooks**: Handle asynchronous Stripe events securely.

## Prerequisites

- **Java 17** or higher
- **Maven** 3.6+
- **Stripe Account**: You need a Stripe account to get API keys.

## Configuration

The application requires the following environment variables to be set. You can set them in your IDE run configuration or in a `.env` file if you are using a loader (though standard Spring Boot reads system env vars).

| Variable | Description |
|----------|-------------|
| `STRIPE_API_KEY` | Your Stripe Secret Key (starts with `sk_test_...` or `sk_live_...`) |
| `STRIPE_WEBHOOK_SECRET` | Your Stripe Webhook Signing Secret (starts with `whsec_...`) |

### Application Properties
Default configuration in `src/main/resources/application.yml`:
- Server Port: `8080`
- Logging: `INFO` level (DEBUG for `com.stripe.integration`)

## Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Hassan-Tarek/Stripe-Integration-API.git
   cd Stripe-Integration-API
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   # Linux/Mac
   export STRIPE_API_KEY=sk_test_...
   export STRIPE_WEBHOOK_SECRET=whsec_...
   mvn spring-boot:run

   # Windows (PowerShell)
   $env:STRIPE_API_KEY="sk_test_..."
   $env:STRIPE_WEBHOOK_SECRET="whsec_..."
   mvn spring-boot:run
   ```

## API Endpoints

The API is structured around the following controllers:

| Resource | Base Path | Description |
|----------|-----------|-------------|
| **Invoices** | `/api/invoice` | Create and retrieve invoices |
| **Payment Intents** | `/api/payment-intent` | Manage payment lifecycles |
| **Subscriptions** | `/api/subscription` | Handle subscriptions |
| **Checkout** | `/api/checkout` | Create checkout sessions |
| **Refunds** | `/api/refund` | Process refunds |
| **Webhooks** | `/api/webhook` | Stripe event listener |

## Project Structure

```
src/main/java/com/stripe/integration
├── config       # Configuration classes
├── controller   # REST Controllers
├── dto          # Data Transfer Objects (Request/Response)
├── service      # Business logic and Stripe SDK calls
└── StripeIntegrationApiApplication.java # Main class
```
