import Data.List (group)
import Control.Monad ((>=>))
import Control.Applicative ((<$>))
import System.Environment (getArgs)

ant :: [[Int]]
ant = iterate(group>=>sequence[length,head])[1]

main :: IO()
main = do
  [n,m] <- map read <$> getArgs
  print $ ant !! n !! m


