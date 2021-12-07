import java.io.*;
import java.nio.file.*;
import java.util.*;


public class SeatBooking implements seatingArrangementInterface{

    private boolean veryStrict = true;
    private HashMap<Character, ArrayList<Integer> > theaterLayout;
    private HashMap<String, Integer> bookingIDList;
    private LinkedHashMap<String, ArrayList<String>> reservationDetails = new LinkedHashMap<String, ArrayList<String>>();
    private int seatsPerRow;
	private int noOfRows;
	private int countAvailableSeats ;

    public SeatBooking(HashMap<Character, ArrayList<Integer>> layout, int seatsPerRow, int noOfRows)
    {
        theaterLayout = layout;
        bookingIDList = new HashMap<>();
        this.seatsPerRow = seatsPerRow;
        this.noOfRows = noOfRows;
        countAvailableSeats = (this.seatsPerRow * this.noOfRows)/2;
    }

    @Override
    public void parseInputFile(String s) throws SeatsNotAvailableException
    {
        s=s.trim();
        String split[] = s.split(" ");
        validateRequest(split);
    }
    

    public void validateRequest(String[] split) throws SeatsNotAvailableException
    {
        String requestId = "";
        int numSeats = 0;
        if(split.length == 2)
        {
            requestId = split[0];
            try
            {
               numSeats = Integer.parseInt(split[1]);
               if(numSeats<0)
               {
                    String noReservation = "Number of tickets cannot be negative";
                    ArrayList<String> res = new ArrayList<>();
                    res.add(noReservation);
                    reservationDetails.put(requestId, res);
                    return;
               }
            }
            catch(NumberFormatException e)
            {
                String noReservation = "Number of tickets cannot contain characters";
                ArrayList<String> res = new ArrayList<>();
                res.add(noReservation);
                reservationDetails.put(requestId, res);
                return;

            }
            bookingIDList.put(requestId, numSeats);
            bookSeats(requestId, numSeats);
            return;
        }

        try{
            if(split.length > 2 || split.length<2)
            {
                throw new SeatsNotAvailableException("Parameters Incorrect");
            }
            if(requestId == null || requestId.isEmpty())
                throw new SeatsNotAvailableException("Request Id is Empty");
        }
        catch(Exception e)
        {
            ArrayList<String> res = new ArrayList<>();
            res.add(e.getMessage());
            reservationDetails.put(requestId , res);
            return;
        }

        
    }


    public void bookSeats(String reqId, int numSeats)
    {
        if(!veryStrict)
        {
            String result = "";
            if(numSeats > countAvailableSeats || numSeats>seatsPerRow)
            {
                String noReservation = "Number of Seats you requested are not available";
                ArrayList<String> res = new ArrayList<>();
                res.add(noReservation);
                reservationDetails.put(reqId, res);
                return;
            }
            Character row = bestFit(numSeats);
            ArrayList<Integer> vacantSeats = theaterLayout.get(row);
            reservationDetails.put(reqId, printSeats(vacantSeats, numSeats, row));
            countAvailableSeats -= numSeats;
            return;
        }

        if(numSeats > countAvailableSeats)
         {
            String noReservation = "No Seats Available";
            ArrayList<String> res = new ArrayList<>();
            res.add(noReservation);
            reservationDetails.put(reqId, res);
            return;
         }
        SafeFit(reqId, numSeats);
        return;
    }

	private ArrayList<String> printSeats(ArrayList<Integer> availableSeats, int numSeats, Character rowId) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < numSeats; i++) {
			result.add(Character.toString(rowId) + availableSeats.get(i));
		}
        for(int i=0;i<numSeats;i++)
        {
            availableSeats.remove(0);
        }

        int covidCount = 3;
        while(availableSeats.size() > 0 && covidCount>0 )
        {
            availableSeats.remove(0);
            countAvailableSeats -= 1;
            covidCount -= 1;
        }
            
		return result;
	}


    public Character bestFit(int numSeats)
    {
        int minSize = Integer.MAX_VALUE;
        Character row = 'A';
        for(var entry : theaterLayout.entrySet())
        {

            if((entry.getValue().size() - numSeats)<minSize && (entry.getValue().size()-numSeats)>=0)
            {
                minSize = entry.getValue().size() - numSeats;
                row = entry.getKey();
            }
        }

        return row;
    }

    private void SafeFit(String reqId, int numSeats)
    {
        char rowId = 'A';
        ArrayList<String> allocatedSeats = new ArrayList<>();
        while(numSeats > 0)
        {
            for(int i=0;i<noOfRows;i=i+2)
            {
                rowId = (char)('A' + noOfRows-i-1);
                var availableSeats = theaterLayout.get(rowId);
                int vacantSeats = theaterLayout.get(rowId).size();
                if(vacantSeats >= numSeats)
                {
                    for (int j = 0; j < numSeats; j++) {
                        allocatedSeats.add(Character.toString(rowId) + availableSeats.get(j));
                    }
                    for(int j=0;j<numSeats;j++)
                    {
                        availableSeats.remove(0);
                    }
                    numSeats = 0;
                }
                else
                {
                    for (int j = 0; j < vacantSeats; j++) {
                        allocatedSeats.add(Character.toString(rowId) + availableSeats.get(j));
                    }
                    for(int j=0;j<vacantSeats;j++)
                    {
                        availableSeats.remove(0);
                    }
                    numSeats -= vacantSeats;
                }

                int covidCount = 3;
                while(availableSeats.size() > 0 && covidCount>0 )
                {
                    availableSeats.remove(0);
                    countAvailableSeats -= 1;
                    covidCount -= 1;
                }

                if(numSeats==0)
                    break;
            }
        }

        reservationDetails.put(reqId, allocatedSeats);
        countAvailableSeats -= numSeats;

    }


    @Override
    public String createFile() throws IOException
    {
        String content = "";
        String newContent="";
        for(var entry : reservationDetails.entrySet())
        {
            content += entry.getKey() + " " + entry.getValue() + System.lineSeparator();
            newContent = content.replace("[", "").replace("]", "");
        }
        String path = createOutputFile("output.txt", newContent);
        return path;
    }

    private String createOutputFile(String fileName, String content) throws IOException
    {
        Files.deleteIfExists(Paths.get(fileName));
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
        File f = new File(fileName);
        return f.getCanonicalPath();
    }
}
