---
description: Generate text based on the project for the user to copy into their documentation
---

# Generate Doc Text

When the user asks to generate text for their documentation, follow these steps:

1. **Read the project structure** – Scan the `src/` directory and subdirectories to understand the current codebase layout (packages, classes, files).

2. **Read relevant source files** – Open and review the specific source files related to the user's request to understand the code content, logic, and relationships.

3. **Ask for context (if needed)** – If the user's request is unclear, ask:
   - Which section of the document is this for? (e.g., architecture, class description, testing, etc.)
   - Any example or format to follow?
   - Any specific detail to include or exclude?

4. **Generate the text** – Based on the code and the user's request, produce clean, concise, and easy-to-understand text that the user can directly copy-paste into their Word/PDF document. Follow these rules:
   - Keep it **brief and clear** – avoid overly technical jargon unless needed.
   - Match the **style/format** of any example the user provides.
   - Use **bullet points, numbered lists, or short paragraphs** as appropriate.
   - Output the text in a **single code block or markdown block** so the user can easily copy it.

5. **Wait for feedback** – After generating, ask the user if they want any adjustments before moving on.
