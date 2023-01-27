# Parallelized Prime Generator

## Summary

Provided is a Prime number generator that finds all prime numbers from 2 to 10^8. It uses a Sieve algorithm to find the prime numbers and parallelizes the process of filling in prime multiples in the array by splitting the work across 8 threads.

It outputs the execution time, the total number of primes found, and the sum of all primes, followed by a list of the 10 largest primes in the range.

## How To Run

Run the following commands:

`javac Main.java`

`java Main`

## Why is there so much Rust Code?

I figured I'd include my earlier attempts at writing the solution in Rust, wherein I got completely blocked because the rust borrow checker is a ruthless entity that shows no mercy to even the most desperate of programmers.
