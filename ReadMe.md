API Endpoints:

1. http://localhost:8081/api/v1/get/points    // getAPI
  --give reward point for all user for last three transactions
   [
   {
   "userId": 1,
   "monthlyPoints": [
   {
   "points": 650,
   "month": "2026-03"
   },
   {
   "points": 850,
   "month": "2026-04"
   },
   {
   "points": 450,
   "month": "2026-02"
   }
   ],
   "total": 1950
   },
   {
   "userId": 2,
   "monthlyPoints": [
   {
   "points": 450,
   "month": "2026-03"
   },
   {
   "points": 650,
   "month": "2026-04"
   },
   {
   "points": 250,
   "month": "2026-02"
   }
   ],
   "total": 1350
   }
   ]
2. http://localhost:8081/api/v1/get/points/1  
  -- get reward point for last three transaction for a particular UserID
   [
   {
   "userId": 1,
   "monthlyPoints": [
   {
   "points": 650,
   "month": "2026-03"
   },
   {
   "points": 850,
   "month": "2026-04"
   },
   {
   "points": 450,
   "month": "2026-02"
   }
   ],
   "total": 1950
   }
   ]
3. http://localhost:8081/api/v1/add/transaction
   --  add a transaction record done by user
  request body
   {
   "userId": 1,
   "amount": 100
   }