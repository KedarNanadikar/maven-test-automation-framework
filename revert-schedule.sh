#!/bin/bash
# 🔄 Script to revert testing schedule back to normal

echo "🔄 Reverting scheduled-tests.yml back to normal schedule..."

# Replace the testing schedule with normal schedule
sed -i 's/# 🚨 TESTING ONLY: Every 5 minutes for 1 hour (remove after testing!)/# Multiple schedules for different test types/' .github/workflows/scheduled-tests.yml
sed -i 's/- cron: '\''*\/5 \* \* \* \*'\''    # Every 5 minutes - FOR TESTING ONLY!/- cron: '\''0 6 \* \* 1-5'\''    # Weekdays at 6 AM UTC (business days)/' .github/workflows/scheduled-tests.yml
sed -i 's/# - cron: '\''0 6 \* \* 1-5'\''    # Weekdays at 6 AM UTC (business days)/- cron: '\''0 12 \* \* 6'\''     # Saturdays at noon UTC (weekend check)/' .github/workflows/scheduled-tests.yml
sed -i 's/# - cron: '\''0 12 \* \* 6'\''     # Saturdays at noon UTC (weekend check)/- cron: '\''0 22 \* \* 0'\''     # Sundays at 10 PM UTC (weekly full run)/' .github/workflows/scheduled-tests.yml

# Restore normal test logic
sed -i 's/# 🚨 TESTING MODE: Run lighter tests every 5 minutes/# Determine which tests to run based on schedule or input/' .github/workflows/scheduled-tests.yml
sed -i 's/SUITE="playwright"/# Different tests for different days/' .github/workflows/scheduled-tests.yml

# Restore normal job name
sed -i 's/🧪 Scheduled Test Execution (🚨 TESTING MODE)/🧪 Scheduled Test Execution/' .github/workflows/scheduled-tests.yml
sed -i 's/timeout-minutes: 10  # Safety timeout for frequent runs//' .github/workflows/scheduled-tests.yml

echo "✅ Reverted to normal schedule!"
echo "📝 Don't forget to commit and push the changes:"
echo "   git add .github/workflows/scheduled-tests.yml"
echo "   git commit -m '🔄 Revert to normal schedule after testing'"
echo "   git push origin feature/ci-cd-integration"