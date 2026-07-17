import { test } from '../fixtures/fixtures';

test.describe('TripStack UI Automation', () => {

    test(
        'Verify user can complete bus booking successfully',
        async ({ bookingFlow }) => {

            await bookingFlow.completeBookingJourney();

        }
    );

});