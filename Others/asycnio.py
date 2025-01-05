import asyncio

# async def -> coroutine

async def task_one():
    print("Task One: starting")
    await asyncio.sleep(3) # non-blocking sleeping for 3 seconds
    print("Task One: Finished after 3 seconds")

async def task_two():
    print("Task Two: starting")
    await asyncio.sleep(1) # non-blocking sleeping for 1 seconds
    print("Task Two: Finished after 1 seconds")
    
async def task_three():
    print("Task Three: starting")
    await asyncio.sleep(2) # non-blocking sleeping for 3 seconds
    print("Task Three: Finished after 2 seconds")

async def main():
    print("Main: Starting all tasks...")
    tasks = [task_one(), task_two(), task_three()]
    await asyncio.gather(*tasks) # run all tasks concurrently
    print("Main: All tasks finished!")

asyncio.run(main())

    
    