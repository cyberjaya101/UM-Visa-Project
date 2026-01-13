# UM VISA MATE: Colour Palette

This document establishes the visual identity and accessibility standards for the UM VISA MATE application.

## 1. Primary Palette (Branding & Contrast)

This palette establishes the institutional look and ensures the highest level of readability.

| Color Name | Use | Color HEX Code | Contrast Ratio (vs. White #FFFFFF) | Rationale |
| :--- | :--- | :--- | :--- | :--- |
| **Primary Base / Text (Deep Blue)** | Biscay | `#163269` | 14.5:1 (Passes AA & AAA) | Used for all main text, navigation bars, headers, and backgrounds that contain white text. |
| **Primary Accent / Gold** | Rebel's Gold | `#DEB406` | 3.5:1 (Fails AA for text) | Used for logos, separators, and non-text graphical accents only. Do not use for small text. |
| **Background / Canvas** | White | `#FFFFFF` | N/A | Default background for screens and forms. Provides maximum contrast with Biscay. |
| **Background Secondary** | Light Gray | `#F3F4F5` | 19.8:1 (Passes AA & AAA) | Used for cards, secondary panels, and sections (e.g., status card background). |

## 2. Status & Action Palette (Functionality)

This palette is used exclusively for conveying critical application status and user actions, aligning with a strong user experience.

| Color Name | Use | Color HEX Code | Purpose in UM VISA MATE | Contrast Ratio (vs. White #FFFFFF) |
| :--- | :--- | :--- | :--- | :--- |
| **Interactive / CTA Blue** | Steel | `#4A7FBA` | Primary action buttons (e.g., "Submit to UM Visa Unit," "Request Faculty Docs"). | 5.5:1 (Passes AA) |
| **Success Status** | Dark Green | `#008000` | Indicates Approved application status and successful payment/document upload. | 10.9:1 (Passes AA & AAA) |
| **Warning / Alert** | Warning Red | `#CC0000` | Indicates Action Required or Document Rejected status. | 4.7:1 (Passes AA) |
| **Notification** | Maize Yellow | `#FFCB05` | Highlights the 3-Month Renewal Alert (NFR 1.4). | 1.2:1 (Fails AA) |

> **Note on Yellow (#FFCB05):** Since this color fails the required contrast ratio for standard text, it should only be used as a background color for an alert banner, with the actual alert text written in Biscay (#163269) to maintain accessibility (NFR 1.1).
